package com.adwiiseagent.service;

import com.adwiiseagent.domain.OrganizationPortfolio;
import com.adwiiseagent.domain.OrganizationPortfolio_;
import com.adwiiseagent.repository.OrganizationPortfolioRepository;
import com.adwiiseagent.service.criteria.OrganizationPortfolioCriteria;
import com.adwiiseagent.service.dto.OrganizationPortfolioDTO;
import com.adwiiseagent.service.mapper.OrganizationPortfolioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class OrganizationPortfolioQueryService extends QueryService<OrganizationPortfolio> {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationPortfolioQueryService.class);

    private final OrganizationPortfolioRepository organizationPortfolioRepository;
    private final OrganizationPortfolioMapper organizationPortfolioMapper;

    public OrganizationPortfolioQueryService(
        OrganizationPortfolioRepository organizationPortfolioRepository,
        OrganizationPortfolioMapper organizationPortfolioMapper
    ) {
        this.organizationPortfolioRepository = organizationPortfolioRepository;
        this.organizationPortfolioMapper = organizationPortfolioMapper;
    }

    public Page<OrganizationPortfolioDTO> findByCriteria(OrganizationPortfolioCriteria criteria, Pageable page) {
        LOG.debug("find organization portfolios by criteria : {}, page: {}", criteria, page);
        return organizationPortfolioRepository.findAll(createSpecification(criteria), page).map(organizationPortfolioMapper::toDto);
    }

    public long countByCriteria(OrganizationPortfolioCriteria criteria) {
        return organizationPortfolioRepository.count(createSpecification(criteria));
    }

    protected Specification<OrganizationPortfolio> createSpecification(OrganizationPortfolioCriteria criteria) {
        Specification<OrganizationPortfolio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrganizationPortfolio_.id));
            }
            if (criteria.getPortfolioName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPortfolioName(), OrganizationPortfolio_.portfolioName));
            }
            if (criteria.getPrimaryTenantId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrimaryTenantId(), OrganizationPortfolio_.primaryTenantId));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), OrganizationPortfolio_.isActive));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), OrganizationPortfolio_.createdAt));
            }
        }
        return specification;
    }
}
