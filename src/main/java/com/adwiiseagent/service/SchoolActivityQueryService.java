package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolActivity;
import com.adwiiseagent.repository.SchoolActivityRepository;
import com.adwiiseagent.service.criteria.SchoolActivityCriteria;
import com.adwiiseagent.service.dto.SchoolActivityDTO;
import com.adwiiseagent.service.mapper.SchoolActivityMapper;
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
public class SchoolActivityQueryService extends QueryService<SchoolActivity> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolActivityQueryService.class);

    private final SchoolActivityRepository schoolActivityRepository;

    private final SchoolActivityMapper schoolActivityMapper;

    public SchoolActivityQueryService(SchoolActivityRepository schoolActivityRepository, SchoolActivityMapper schoolActivityMapper) {
        this.schoolActivityRepository = schoolActivityRepository;
        this.schoolActivityMapper = schoolActivityMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolActivityDTO> findByCriteria(SchoolActivityCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolActivity> specification = createSpecification(criteria);
        return schoolActivityRepository.findAll(specification, page).map(schoolActivityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolActivityCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolActivity> specification = createSpecification(criteria);
        return schoolActivityRepository.count(specification);
    }

    protected Specification<SchoolActivity> createSpecification(SchoolActivityCriteria criteria) {
        Specification<SchoolActivity> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolActivity_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolActivity_.tenantId) : null,
                    criteria.getActivityKind() != null
                        ? buildStringSpecification(criteria.getActivityKind(), SchoolActivity_.activityKind)
                        : null,
                    criteria.getName() != null ? buildStringSpecification(criteria.getName(), SchoolActivity_.name) : null,
                    criteria.getSlug() != null ? buildStringSpecification(criteria.getSlug(), SchoolActivity_.slug) : null,
                    criteria.getSummary() != null ? buildStringSpecification(criteria.getSummary(), SchoolActivity_.summary) : null,
                    criteria.getBody() != null ? buildStringSpecification(criteria.getBody(), SchoolActivity_.body) : null,
                    criteria.getImageUrl() != null ? buildStringSpecification(criteria.getImageUrl(), SchoolActivity_.imageUrl) : null,
                    criteria.getSortOrder() != null ? buildRangeSpecification(criteria.getSortOrder(), SchoolActivity_.sortOrder) : null,
                    criteria.getIsActive() != null ? buildSpecification(criteria.getIsActive(), SchoolActivity_.isActive) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
