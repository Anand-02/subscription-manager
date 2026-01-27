package com.firstclub.subscription_manager.controller;

import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.entity.UserSubscription;
import com.firstclub.subscription_manager.service.SubscriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @PostMapping("/user/{userId}/plan/{planId}")
    public ResponseEntity<UserSubscription> subscribe(@PathVariable Long userId, @PathVariable Long planId){
        return new ResponseEntity<>(subscriptionService.createSubscription(userId, planId), HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{userId}")
    public ResponseEntity<HttpStatus> cancelSubscription(@PathVariable Long userId){
        subscriptionService.cancelSubscription(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserSubscription> getUserSubscription(@PathVariable Long userId){
        return new ResponseEntity<>(subscriptionService.getActivePlanForUser(userId), HttpStatus.OK);
    }

    @PutMapping("/update/{userId}/plan/{planId}")
    public ResponseEntity<UserSubscription> updateSubscription(@PathVariable Long userId, @PathVariable Long planId){
        return new ResponseEntity<>(subscriptionService.upgradePlan(userId, planId), HttpStatus.OK);
    }
}
