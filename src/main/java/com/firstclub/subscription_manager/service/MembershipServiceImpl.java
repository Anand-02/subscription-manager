package com.firstclub.subscription_manager.service;

import com.firstclub.subscription_manager.entity.MembershipPlan;
import com.firstclub.subscription_manager.repository.MembershipRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void addMembershipPlan(MembershipPlan membershipPlan) {
        membershipRepository.save(membershipPlan);
    }

    @Override
    @Transactional
    public void deleteMembershipPlan(Long planId) {
        membershipRepository.deleteById(planId);
    }
}
