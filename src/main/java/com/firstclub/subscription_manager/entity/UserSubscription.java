package com.firstclub.subscription_manager.entity;

import com.firstclub.subscription_manager.enums.SubscriptionStatus;
import com.firstclub.subscription_manager.exception.ActiveSubscriptionNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_subscriptions")
@Getter
@NoArgsConstructor
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    public UserSubscription(Long userId, Long planId,
                            LocalDateTime startTime, LocalDateTime endTime) {

        if (userId == null) throw new IllegalArgumentException("UserId required");
        if (planId == null) throw new IllegalArgumentException("PlanId required");
        if (endTime.isBefore(startTime))
            throw new IllegalArgumentException("End time cannot be before start time");

        this.userId = userId;
        this.planId = planId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = SubscriptionStatus.ACTIVE;
    }

    public Boolean isExpired() {
        if (endTime.isBefore(LocalDateTime.now())) {
            status = SubscriptionStatus.EXPIRED;
            return true;
        }
        return false;
    }

    public void markCancelled() {
        if(isExpired()) {
            throw new ActiveSubscriptionNotFoundException("Only active subscriptions can be cancelled");
        }
        this.status = SubscriptionStatus.CANCELLED;
        this.endTime = LocalDateTime.now();
    }

    public void markExpired() {
        if(isExpired()) {
            throw new ActiveSubscriptionNotFoundException("Not active subscription found");
        }
        this.status = SubscriptionStatus.EXPIRED;
    }

    public void upgradePlan(Long newPlanId, Duration newDuration) {
        if(isExpired()){
            throw new ActiveSubscriptionNotFoundException("No active subscription found");
        }
        this.planId = newPlanId;
        this.startTime = LocalDateTime.now();
        this.endTime = LocalDateTime.now().plus(newDuration);
    }
}
