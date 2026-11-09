package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolDepartment;
import com.adwiiseagent.repository.SchoolDepartmentRepository;
import com.adwiiseagent.service.criteria.SchoolDepartmentCriteria;
import com.adwiiseagent.service.dto.SchoolDepartmentDTO;
import com.adwiiseagent.service.mapper.SchoolDepartmentMapper;
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
public class SchoolDepartmentQueryService extends QueryService<SchoolDepartment> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolDepartmentQueryService.class);

    private final SchoolDepartmentRepository schoolDepartmentRepository;

    private final SchoolDepartmentMapper schoolDepartmentMapper;

    public SchoolDepartmentQueryService(
        SchoolDepartmentRepository schoolDepartmentRepository,
        SchoolDepartmentMapper schoolDepartmentMapper
    ) {
        this.schoolDepartmentRepository = schoolDepartmentRepository;
        this.schoolDepartmentMapper = schoolDepartmentMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolDepartmentDTO> findByCriteria(SchoolDepartmentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolDepartment> specification = createSpecification(criteria);
        return schoolDepartmentRepository.findAll(specification, page).map(schoolDepartmentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolDepartmentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolDepartment> specification = createSpecification(criteria);
        return schoolDepartmentRepository.count(specification);
    }

    protected Specification<SchoolDepartment> createSpecification(SchoolDepartmentCriteria criteria) {
        Specification<SchoolDepartment> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolDepartment_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolDepartment_.tenantId) : null,
                    criteria.getName() != null ? buildStringSpecification(criteria.getName(), SchoolDepartment_.name) : null,
                    criteria.getSlug() != null ? buildStringSpecification(criteria.getSlug(), SchoolDepartment_.slug) : null,
                    criteria.getSummary() != null ? buildStringSpecification(criteria.getSummary(), SchoolDepartment_.summary) : null,
                    criteria.getBody() != null ? buildStringSpecification(criteria.getBody(), SchoolDepartment_.body) : null,
                    criteria.getImageUrl() != null ? buildStringSpecification(criteria.getImageUrl(), SchoolDepartment_.imageUrl) : null,
                    criteria.getHeadStaffId() != null
                        ? buildRangeSpecification(criteria.getHeadStaffId(), SchoolDepartment_.headStaffId)
                        : null,
                    criteria.getSortOrder() != null ? buildRangeSpecification(criteria.getSortOrder(), SchoolDepartment_.sortOrder) : null,
                    criteria.getIsActive() != null ? buildSpecification(criteria.getIsActive(), SchoolDepartment_.isActive) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
