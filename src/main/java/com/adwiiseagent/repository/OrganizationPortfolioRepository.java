package com.adwiiseagent.repository;

import com.adwiiseagent.domain.OrganizationPortfolio;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationPortfolioRepository
    extends JpaRepository<OrganizationPortfolio, Long>, JpaSpecificationExecutor<OrganizationPortfolio> {
    boolean existsByPrimaryTenantId(String primaryTenantId);

    List<OrganizationPortfolio> findByPrimaryTenantIdInAndIsActiveTrue(Collection<String> primaryTenantIds);
}
