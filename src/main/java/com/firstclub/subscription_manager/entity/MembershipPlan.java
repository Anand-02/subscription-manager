package com.firstclub.subscription_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Entity
@Table(name = "membership_plan")
@NoArgsConstructor
public class MembershipPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long planId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "duration", nullable = false)
    private Duration duration;
}
