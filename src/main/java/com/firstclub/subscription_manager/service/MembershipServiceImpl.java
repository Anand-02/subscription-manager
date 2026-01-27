package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Override
    public List<MembershipPlan> getMembershipPlans() {
        return membershipRepository.findAll();
    }

    @Override
    public void addMembershipPlan(MembershipPlan membershipPlan) {
        membershipRepository.save(membershipPlan);
    }

    @Override
    public void deleteMembershipPlan(Long planId) {
        membershipRepository.deleteById(planId);
    }
}
