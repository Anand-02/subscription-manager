package com.firstclub.subscription_manager.repository;

import com.firstclub.subscription_manager.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    Optional<UserSubscription> findByUserId(Long userId);

    Optional<UserSubscription> findByUserIdAndPlanId(Long userId,  Long planId);
}
