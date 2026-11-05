package com.adwiiseagent.service;

import com.adwiiseagent.domain.OrganizationPortfolioMember;
import com.adwiiseagent.domain.OrganizationPortfolioMember_;
import com.adwiiseagent.repository.OrganizationPortfolioMemberRepository;
import com.adwiiseagent.service.criteria.OrganizationPortfolioMemberCriteria;
import com.adwiiseagent.service.dto.OrganizationPortfolioMemberDTO;
import com.adwiiseagent.service.mapper.OrganizationPortfolioMemberMapper;
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
public class OrganizationPortfolioMemberQueryService extends QueryService<OrganizationPortfolioMember> {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationPortfolioMemberQueryService.class);

    private final OrganizationPortfolioMemberRepository organizationPortfolioMemberRepository;
    private final OrganizationPortfolioMemberMapper organizationPortfolioMemberMapper;

    public OrganizationPortfolioMemberQueryService(
        OrganizationPortfolioMemberRepository organizationPortfolioMemberRepository,
        OrganizationPortfolioMemberMapper organizationPortfolioMemberMapper
    ) {
        this.organizationPortfolioMemberRepository = organizationPortfolioMemberRepository;
        this.organizationPortfolioMemberMapper = organizationPortfolioMemberMapper;
    }

    public Page<OrganizationPortfolioMemberDTO> findByCriteria(OrganizationPortfolioMemberCriteria criteria, Pageable page) {
        LOG.debug("find organization portfolio members by criteria : {}, page: {}", criteria, page);
        return organizationPortfolioMemberRepository
            .findAll(createSpecification(criteria), page)
            .map(organizationPortfolioMemberMapper::toDto);
    }

    public long countByCriteria(OrganizationPortfolioMemberCriteria criteria) {
        return organizationPortfolioMemberRepository.count(createSpecification(criteria));
    }

    protected Specification<OrganizationPortfolioMember> createSpecification(OrganizationPortfolioMemberCriteria criteria) {
        Specification<OrganizationPortfolioMember> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrganizationPortfolioMember_.id));
            }
            if (criteria.getPortfolioId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPortfolioId(), OrganizationPortfolioMember_.portfolioId));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), OrganizationPortfolioMember_.tenantId));
            }
            if (criteria.getIsPrimary() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPrimary(), OrganizationPortfolioMember_.isPrimary));
            }
        }
        return specification;
    }
}
