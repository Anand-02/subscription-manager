package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.entity.UserSubscription;

public interface SubscriptionService {
    UserSubscription createSubscription(Long userId, Long planId);
    UserSubscription cancelSubscription(Long userId);
    UserSubscription upgradePlan(Long userId, Long planId);
    UserSubscription getActivePlanForUser(Long userId);
}
