package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.UserSubscription;
import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.repository.MembershipRepository;
import com.firstclub.subscription_manager.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubscriptionServiceImpl implements  SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Override
    public void createSubscription(Long userId, Long planId) {
        MembershipPlan membershipPlan = membershipRepository.findById(planId).get();
        Duration duration = membershipPlan.getDuration();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plus(duration);
        subscriptionRepository.save(new UserSubscription(
                userId,
                planId,
                now,
                endTime));
    }

    @Override
    public void cancelSubscription(Long userId, Long planId) {
        UserSubscription userSubscription = subscriptionRepository.
                findByUserIdAndPlanId(userId, planId).get();
        userSubscription.markCancelled();
        subscriptionRepository.save(userSubscription);
    }

    @Override
    public void upgradePlan(Long userId, Long planId) {

    }

    @Override
    public MembershipPlan getPlanByUserId(Long userId) {
        UserSubscription subscription = subscriptionRepository.findByUserId(userId).get();
        return membershipRepository.findById(subscription.getPlanId()).get();
    }
}
