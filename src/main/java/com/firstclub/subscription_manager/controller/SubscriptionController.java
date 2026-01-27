package com.firstclub.subscription_manager.controller;

import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.entity.UserSubscription;
import com.firstclub.subscription_manager.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/user/{userId}/plan/{planId}")
    public ResponseEntity<UserSubscription> subscribe(@PathVariable Long userId, @PathVariable Long planId){
        return new ResponseEntity<>(subscriptionService.createSubscription(userId, planId), HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{userId}/plan/{planId}")
    public ResponseEntity<HttpStatus> cancelSubscription(@PathVariable Long userId, @PathVariable Long planId){
        subscriptionService.cancelSubscription(userId, planId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MembershipPlan> getUserSubscription(@PathVariable Long userId){
        return new ResponseEntity<>(subscriptionService.getPlanByUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/update/{userId}/plan/{planId}")
    public ResponseEntity<UserSubscription> updateSubscription(@PathVariable Long userId, @PathVariable Long planId){
        return new ResponseEntity<>(subscriptionService.upgradePlan(userId, planId), HttpStatus.OK);
    }
}
