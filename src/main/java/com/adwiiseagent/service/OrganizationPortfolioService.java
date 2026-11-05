package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.OrganizationPortfolioDTO;
import com.adwiiseagent.service.dto.OrganizationPortfolioMemberDTO;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrganizationPortfolioService {
    OrganizationPortfolioDTO save(OrganizationPortfolioDTO dto);

    OrganizationPortfolioDTO update(OrganizationPortfolioDTO dto);

    Optional<OrganizationPortfolioDTO> partialUpdate(OrganizationPortfolioDTO dto);

    Optional<OrganizationPortfolioDTO> findOne(Long id);

    void delete(Long id);

    /** Rejects when the portfolio is missing or inactive. Used before onboarding writes anything. */
    void requireActivePortfolio(Long id);

    List<OrganizationPortfolioMemberDTO> findMembers(Long portfolioId);

    OrganizationPortfolioMemberDTO addMember(Long portfolioId, String tenantId, boolean asPrimary);

    void removeMember(Long portfolioId, Long memberId);

    /**
     * Member tenant ids of active portfolios whose primary tenant is in {@code primaryTenantIds}.
     * Includes the primary tenant itself.
     */
    List<String> findMemberTenantIdsForPrimaries(Collection<String> primaryTenantIds);
}
