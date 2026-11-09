package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolHighlight;
import com.adwiiseagent.repository.SchoolHighlightRepository;
import com.adwiiseagent.service.criteria.SchoolHighlightCriteria;
import com.adwiiseagent.service.dto.SchoolHighlightDTO;
import com.adwiiseagent.service.mapper.SchoolHighlightMapper;
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
public class SchoolHighlightQueryService extends QueryService<SchoolHighlight> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolHighlightQueryService.class);

    private final SchoolHighlightRepository schoolHighlightRepository;

    private final SchoolHighlightMapper schoolHighlightMapper;

    public SchoolHighlightQueryService(SchoolHighlightRepository schoolHighlightRepository, SchoolHighlightMapper schoolHighlightMapper) {
        this.schoolHighlightRepository = schoolHighlightRepository;
        this.schoolHighlightMapper = schoolHighlightMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolHighlightDTO> findByCriteria(SchoolHighlightCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolHighlight> specification = createSpecification(criteria);
        return schoolHighlightRepository.findAll(specification, page).map(schoolHighlightMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolHighlightCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolHighlight> specification = createSpecification(criteria);
        return schoolHighlightRepository.count(specification);
    }

    protected Specification<SchoolHighlight> createSpecification(SchoolHighlightCriteria criteria) {
        Specification<SchoolHighlight> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolHighlight_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolHighlight_.tenantId) : null,
                    criteria.getPersonName() != null
                        ? buildStringSpecification(criteria.getPersonName(), SchoolHighlight_.personName)
                        : null,
                    criteria.getSubtitle() != null ? buildStringSpecification(criteria.getSubtitle(), SchoolHighlight_.subtitle) : null,
                    criteria.getImageUrl() != null ? buildStringSpecification(criteria.getImageUrl(), SchoolHighlight_.imageUrl) : null,
                    criteria.getAcademicYear() != null
                        ? buildStringSpecification(criteria.getAcademicYear(), SchoolHighlight_.academicYear)
                        : null,
                    criteria.getSortOrder() != null ? buildRangeSpecification(criteria.getSortOrder(), SchoolHighlight_.sortOrder) : null,
                    criteria.getIsActive() != null ? buildSpecification(criteria.getIsActive(), SchoolHighlight_.isActive) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
