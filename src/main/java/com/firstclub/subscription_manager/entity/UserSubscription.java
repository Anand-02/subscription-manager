package com.firstclub.subscription_manager.entity;

import com.firstclub.subscription_manager.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        return status != SubscriptionStatus.CANCELLED &&
                LocalDateTime.now().isAfter(endTime);
    }

    public SubscriptionStatus getSubscriptionStatus() {
        if (status == SubscriptionStatus.CANCELLED)
            return SubscriptionStatus.CANCELLED;
        if (isExpired())
            return SubscriptionStatus.EXPIRED;
        return SubscriptionStatus.ACTIVE;
    }

    public void markCancelled() {
        if(status != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException("Only active subscriptions can be cancelled");
        }
        this.status = SubscriptionStatus.CANCELLED;
        this.endTime = LocalDateTime.now();
    }

    public void markExpired() {
        if(status != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException("");
        }
        this.status = SubscriptionStatus.EXPIRED;
    }
}
