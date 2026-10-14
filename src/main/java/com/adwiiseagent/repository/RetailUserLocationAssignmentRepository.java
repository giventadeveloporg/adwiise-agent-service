package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RetailUserLocationAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailUserLocationAssignmentRepository
    extends JpaRepository<RetailUserLocationAssignment, Long>, JpaSpecificationExecutor<RetailUserLocationAssignment> {
    List<RetailUserLocationAssignment> findByTenantIdAndUserProfileId(String tenantId, Long userProfileId);
}
