package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.UserSubscription;
import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.enums.SubscriptionStatus;
import com.firstclub.subscription_manager.exception.ActiveSubscriptionNotFoundException;
import com.firstclub.subscription_manager.exception.PlanNotFoundException;
import com.firstclub.subscription_manager.repository.MembershipRepository;
import com.firstclub.subscription_manager.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

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
        return subscriptionRepository.save(new UserSubscription(
                userId,
                planId,
                now,
                endTime));
    }

    @Override
    @Transactional
    public UserSubscription cancelSubscription(Long userId) {
        UserSubscription userSubscription = subscriptionRepository.
                findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .orElseThrow(()-> new ActiveSubscriptionNotFoundException("No active subscription present for userId: " + userId));
        userSubscription.markCancelled();
        return subscriptionRepository.save(userSubscription);
    }

    @Override
    @Transactional
    public UserSubscription upgradePlan(Long userId, Long planId) {
        return null;
    }

    @Override
    public UserSubscription getActivePlanForUser(Long userId) {
        return subscriptionRepository
                .findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .orElseThrow(()-> new ActiveSubscriptionNotFoundException("Active subscription not found for user: " + userId));
    }
}
