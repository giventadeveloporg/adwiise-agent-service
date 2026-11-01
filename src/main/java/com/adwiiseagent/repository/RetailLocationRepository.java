package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RetailLocation;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailLocationRepository extends JpaRepository<RetailLocation, Long>, JpaSpecificationExecutor<RetailLocation> {
    List<RetailLocation> findByTenantIdAndRegionIn(String tenantId, Collection<String> regions);

    long countByTenantIdAndRetailerTypeCode(String tenantId, String retailerTypeCode);

    boolean existsByRetailerTypeCode(String retailerTypeCode);
}
