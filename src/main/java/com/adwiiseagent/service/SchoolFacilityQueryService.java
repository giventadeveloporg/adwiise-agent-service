package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolFacility;
import com.adwiiseagent.repository.SchoolFacilityRepository;
import com.adwiiseagent.service.criteria.SchoolFacilityCriteria;
import com.adwiiseagent.service.dto.SchoolFacilityDTO;
import com.adwiiseagent.service.mapper.SchoolFacilityMapper;
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
public class SchoolFacilityQueryService extends QueryService<SchoolFacility> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFacilityQueryService.class);

    private final SchoolFacilityRepository schoolFacilityRepository;

    private final SchoolFacilityMapper schoolFacilityMapper;

    public SchoolFacilityQueryService(SchoolFacilityRepository schoolFacilityRepository, SchoolFacilityMapper schoolFacilityMapper) {
        this.schoolFacilityRepository = schoolFacilityRepository;
        this.schoolFacilityMapper = schoolFacilityMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolFacilityDTO> findByCriteria(SchoolFacilityCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolFacility> specification = createSpecification(criteria);
        return schoolFacilityRepository.findAll(specification, page).map(schoolFacilityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolFacilityCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolFacility> specification = createSpecification(criteria);
        return schoolFacilityRepository.count(specification);
    }

    protected Specification<SchoolFacility> createSpecification(SchoolFacilityCriteria criteria) {
        Specification<SchoolFacility> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolFacility_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolFacility_.tenantId) : null,
                    criteria.getName() != null ? buildStringSpecification(criteria.getName(), SchoolFacility_.name) : null,
                    criteria.getSlug() != null ? buildStringSpecification(criteria.getSlug(), SchoolFacility_.slug) : null,
                    criteria.getSummary() != null ? buildStringSpecification(criteria.getSummary(), SchoolFacility_.summary) : null,
                    criteria.getBody() != null ? buildStringSpecification(criteria.getBody(), SchoolFacility_.body) : null,
                    criteria.getImageUrl() != null ? buildStringSpecification(criteria.getImageUrl(), SchoolFacility_.imageUrl) : null,
                    criteria.getSortOrder() != null ? buildRangeSpecification(criteria.getSortOrder(), SchoolFacility_.sortOrder) : null,
                    criteria.getIsActive() != null ? buildSpecification(criteria.getIsActive(), SchoolFacility_.isActive) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
