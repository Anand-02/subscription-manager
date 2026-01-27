package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.MembershipPlan;

import java.util.List;

public interface MembershipService {
    List<MembershipPlan> getMembershipPlans();
    void addMembershipPlan(MembershipPlan membershipPlan);
    void deleteMembershipPlan(Long planId);
}
