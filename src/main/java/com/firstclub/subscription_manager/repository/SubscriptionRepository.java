package com.firstclub.subscription_manager.repository;

import com.firstclub.subscription_manager.entity.UserSubscription;
import com.firstclub.subscription_manager.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    Optional<UserSubscription> findByUserId(Long userId);

    Optional<UserSubscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);
}
