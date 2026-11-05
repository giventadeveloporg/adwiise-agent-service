package com.adwiiseagent.repository;

import com.adwiiseagent.domain.OrganizationPortfolioMember;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationPortfolioMemberRepository
    extends JpaRepository<OrganizationPortfolioMember, Long>, JpaSpecificationExecutor<OrganizationPortfolioMember> {
    boolean existsByTenantId(String tenantId);

    Optional<OrganizationPortfolioMember> findByTenantId(String tenantId);

    Optional<OrganizationPortfolioMember> findByPortfolioIdAndIsPrimaryTrue(Long portfolioId);

    List<OrganizationPortfolioMember> findByPortfolioIdOrderByIsPrimaryDescTenantIdAsc(Long portfolioId);

    List<OrganizationPortfolioMember> findByPortfolioIdIn(Collection<Long> portfolioIds);

    void deleteByPortfolioId(Long portfolioId);
}
