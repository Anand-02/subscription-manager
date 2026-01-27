package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.MembershipPlan;

public interface SubscriptionService {
    void createSubscription(Long userId, Long planId);
    void cancelSubscription(Long userId, Long planId);
    void upgradePlan(Long userId, Long planId);
    MembershipPlan getPlanByUserId(Long userId);
}
