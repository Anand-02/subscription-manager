package com.firstclub.subscription_manager.controller;

import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping("/plans/all")
    public ResponseEntity<List<MembershipPlan>> getAllPlans(){
        return new ResponseEntity<>(membershipService.getMembershipPlans(), HttpStatus.OK);
    }

    @PostMapping("/plan")
    public ResponseEntity<Object> addMembershipPlan(@RequestBody MembershipPlan membershipPlan){
        membershipService.addMembershipPlan(membershipPlan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/plan")
    public ResponseEntity<Object> deleteMembershipPlan(@RequestBody Long planId){
        membershipService.deleteMembershipPlan(planId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
