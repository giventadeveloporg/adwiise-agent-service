package com.adwiiseagent.repository;

import com.adwiiseagent.domain.PlatformBillingAssignmentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformBillingAssignmentEventRepository
    extends JpaRepository<PlatformBillingAssignmentEvent, Long>, JpaSpecificationExecutor<PlatformBillingAssignmentEvent> {}
