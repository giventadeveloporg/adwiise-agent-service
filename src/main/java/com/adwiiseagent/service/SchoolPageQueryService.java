package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolPage;
import com.adwiiseagent.repository.SchoolPageRepository;
import com.adwiiseagent.service.criteria.SchoolPageCriteria;
import com.adwiiseagent.service.dto.SchoolPageDTO;
import com.adwiiseagent.service.mapper.SchoolPageMapper;
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
public class SchoolPageQueryService extends QueryService<SchoolPage> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolPageQueryService.class);

    private final SchoolPageRepository schoolPageRepository;

    private final SchoolPageMapper schoolPageMapper;

    public SchoolPageQueryService(SchoolPageRepository schoolPageRepository, SchoolPageMapper schoolPageMapper) {
        this.schoolPageRepository = schoolPageRepository;
        this.schoolPageMapper = schoolPageMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolPageDTO> findByCriteria(SchoolPageCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolPage> specification = createSpecification(criteria);
        return schoolPageRepository.findAll(specification, page).map(schoolPageMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolPageCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolPage> specification = createSpecification(criteria);
        return schoolPageRepository.count(specification);
    }

    protected Specification<SchoolPage> createSpecification(SchoolPageCriteria criteria) {
        Specification<SchoolPage> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolPage_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolPage_.tenantId) : null,
                    criteria.getSlug() != null ? buildStringSpecification(criteria.getSlug(), SchoolPage_.slug) : null,
                    criteria.getTitle() != null ? buildStringSpecification(criteria.getTitle(), SchoolPage_.title) : null,
                    criteria.getSummary() != null ? buildStringSpecification(criteria.getSummary(), SchoolPage_.summary) : null,
                    criteria.getBody() != null ? buildStringSpecification(criteria.getBody(), SchoolPage_.body) : null,
                    criteria.getImageUrl() != null ? buildStringSpecification(criteria.getImageUrl(), SchoolPage_.imageUrl) : null,
                    criteria.getSortOrder() != null ? buildRangeSpecification(criteria.getSortOrder(), SchoolPage_.sortOrder) : null,
                    criteria.getIsActive() != null ? buildSpecification(criteria.getIsActive(), SchoolPage_.isActive) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
