package com.adwiiseagent.service;

import com.adwiiseagent.domain.TenantTournament;
import com.adwiiseagent.domain.TenantTournament_;
import com.adwiiseagent.repository.TenantTournamentRepository;
import com.adwiiseagent.service.criteria.TenantTournamentCriteria;
import com.adwiiseagent.service.dto.TenantTournamentDTO;
import com.adwiiseagent.service.mapper.TenantTournamentMapper;
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
public class TenantTournamentQueryService extends QueryService<TenantTournament> {

    private static final Logger LOG = LoggerFactory.getLogger(TenantTournamentQueryService.class);

    private final TenantTournamentRepository tenantTournamentRepository;
    private final TenantTournamentMapper tenantTournamentMapper;

    public TenantTournamentQueryService(
        TenantTournamentRepository tenantTournamentRepository,
        TenantTournamentMapper tenantTournamentMapper
    ) {
        this.tenantTournamentRepository = tenantTournamentRepository;
        this.tenantTournamentMapper = tenantTournamentMapper;
    }

    @Transactional(readOnly = true)
    public Page<TenantTournamentDTO> findByCriteria(TenantTournamentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TenantTournament> specification = createSpecification(criteria);
        return tenantTournamentRepository.findAll(specification, page).map(tenantTournamentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(TenantTournamentCriteria criteria) {
        final Specification<TenantTournament> specification = createSpecification(criteria);
        return tenantTournamentRepository.count(specification);
    }

    protected Specification<TenantTournament> createSpecification(TenantTournamentCriteria criteria) {
        Specification<TenantTournament> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }

            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TenantTournament_.id));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), TenantTournament_.tenantId));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), TenantTournament_.title));
            }
            if (criteria.getShortLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortLabel(), TenantTournament_.shortLabel));
            }
            if (criteria.getSlug() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlug(), TenantTournament_.slug));
            }
            if (criteria.getEditionYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditionYear(), TenantTournament_.editionYear));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TenantTournament_.status));
            }
            if (criteria.getShowInHeader() != null) {
                specification = specification.and(buildSpecification(criteria.getShowInHeader(), TenantTournament_.showInHeader));
            }
            if (criteria.getIsPublished() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPublished(), TenantTournament_.isPublished));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), TenantTournament_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), TenantTournament_.endDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), TenantTournament_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), TenantTournament_.updatedAt));
            }
        }
        return specification;
    }
}
