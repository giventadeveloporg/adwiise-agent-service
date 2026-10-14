package com.adwiiseagent.repository;

import com.adwiiseagent.domain.PlatformBillingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformBillingPlanRepository
    extends JpaRepository<PlatformBillingPlan, Long>, JpaSpecificationExecutor<PlatformBillingPlan> {}
