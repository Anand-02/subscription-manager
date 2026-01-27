package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.UserSubscription;
import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.enums.SubscriptionStatus;
import com.firstclub.subscription_manager.exception.ActiveSubscriptionNotFoundException;
import com.firstclub.subscription_manager.exception.PlanNotFoundException;
import com.firstclub.subscription_manager.exception.SubscriptionExpiredException;
import com.firstclub.subscription_manager.repository.MembershipRepository;
import com.firstclub.subscription_manager.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements  SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Override
    @Transactional
    public UserSubscription createSubscription(Long userId, Long planId) {
        MembershipPlan membershipPlan = membershipRepository
                .findById(planId)
                .orElseThrow(() -> new PlanNotFoundException("No plan present for planId: " + planId));
        Duration duration = membershipPlan.getDuration();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plus(duration);
        Optional<UserSubscription> userSubscription = subscriptionRepository
                .findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
        if(userSubscription.isPresent()){
            boolean isExpired = userSubscription.get().isExpired();
            if(!isExpired){
                throw new IllegalStateException("Subscription already exists!");
            }
        }
        return subscriptionRepository.save(new UserSubscription(
                userId,
                planId,
                now,
                endTime));
    }

    @Override
    @Transactional(noRollbackFor = SubscriptionExpiredException.class)
    public UserSubscription cancelSubscription(Long userId) {
        UserSubscription userSubscription = subscriptionRepository.
                findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .orElseThrow(()-> new ActiveSubscriptionNotFoundException("No active subscription present for userId: " + userId));

        checkExpiry(userSubscription);
        userSubscription.markCancelled();
        return subscriptionRepository.save(userSubscription);
    }

    @Override
    @Transactional(noRollbackFor = SubscriptionExpiredException.class)
    public UserSubscription upgradePlan(Long userId, Long planId) {
        UserSubscription activeSubscription = subscriptionRepository
                .findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .orElseThrow(()->  new ActiveSubscriptionNotFoundException("No active subscription present for userId: " + userId));

        checkExpiry(activeSubscription);
        MembershipPlan newPlan = membershipRepository
                .findById(planId)
                .orElseThrow(() -> new PlanNotFoundException("No plan present for planId: " + planId));

        if(planId.equals(activeSubscription.getPlanId())){
            throw new IllegalArgumentException("Cannot upgrade to same plan");
        }
        activeSubscription.upgradePlan(planId, newPlan.getDuration());
        return subscriptionRepository.save(activeSubscription);
    }

    @Override
    public UserSubscription getActivePlanForUser(Long userId) {
        return subscriptionRepository
                .findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .orElseThrow(()-> new ActiveSubscriptionNotFoundException("Active subscription not found for user: " + userId));
    }

    public void checkExpiry(UserSubscription userSubscription) {
        if(userSubscription.isExpired()){
            userSubscription.markExpired();
            subscriptionRepository.save(userSubscription);
            throw new SubscriptionExpiredException("Subscription expired");
        }
    }
}
