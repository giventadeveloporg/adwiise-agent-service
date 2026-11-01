package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RetailUserRegionAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailUserRegionAssignmentRepository
    extends JpaRepository<RetailUserRegionAssignment, Long>, JpaSpecificationExecutor<RetailUserRegionAssignment> {
    List<RetailUserRegionAssignment> findByTenantIdAndUserProfileId(String tenantId, Long userProfileId);
}
