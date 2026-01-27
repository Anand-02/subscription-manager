package com.firstclub.subscription_manager.repository;

import com.firstclub.subscription_manager.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<MembershipPlan, Long> {

}
