package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolStaffMember;
import com.adwiiseagent.repository.SchoolStaffMemberRepository;
import com.adwiiseagent.service.criteria.SchoolStaffMemberCriteria;
import com.adwiiseagent.service.dto.SchoolStaffMemberDTO;
import com.adwiiseagent.service.mapper.SchoolStaffMemberMapper;
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
public class SchoolStaffMemberQueryService extends QueryService<SchoolStaffMember> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolStaffMemberQueryService.class);

    private final SchoolStaffMemberRepository schoolStaffMemberRepository;

    private final SchoolStaffMemberMapper schoolStaffMemberMapper;

    public SchoolStaffMemberQueryService(
        SchoolStaffMemberRepository schoolStaffMemberRepository,
        SchoolStaffMemberMapper schoolStaffMemberMapper
    ) {
        this.schoolStaffMemberRepository = schoolStaffMemberRepository;
        this.schoolStaffMemberMapper = schoolStaffMemberMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolStaffMemberDTO> findByCriteria(SchoolStaffMemberCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolStaffMember> specification = createSpecification(criteria);
        return schoolStaffMemberRepository.findAll(specification, page).map(schoolStaffMemberMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolStaffMemberCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolStaffMember> specification = createSpecification(criteria);
        return schoolStaffMemberRepository.count(specification);
    }

    protected Specification<SchoolStaffMember> createSpecification(SchoolStaffMemberCriteria criteria) {
        Specification<SchoolStaffMember> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolStaffMember_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolStaffMember_.tenantId) : null,
                    criteria.getStaffCategory() != null
                        ? buildStringSpecification(criteria.getStaffCategory(), SchoolStaffMember_.staffCategory)
                        : null,
                    criteria.getStage() != null ? buildStringSpecification(criteria.getStage(), SchoolStaffMember_.stage) : null,
                    criteria.getFirstName() != null
                        ? buildStringSpecification(criteria.getFirstName(), SchoolStaffMember_.firstName)
                        : null,
                    criteria.getLastName() != null ? buildStringSpecification(criteria.getLastName(), SchoolStaffMember_.lastName) : null,
                    criteria.getDesignation() != null
                        ? buildStringSpecification(criteria.getDesignation(), SchoolStaffMember_.designation)
                        : null,
                    criteria.getQualification() != null
                        ? buildStringSpecification(criteria.getQualification(), SchoolStaffMember_.qualification)
                        : null,
                    criteria.getSubjectName() != null
                        ? buildStringSpecification(criteria.getSubjectName(), SchoolStaffMember_.subjectName)
                        : null,
                    criteria.getClassTeacherOf() != null
                        ? buildStringSpecification(criteria.getClassTeacherOf(), SchoolStaffMember_.classTeacherOf)
                        : null,
                    criteria.getDepartmentId() != null
                        ? buildRangeSpecification(criteria.getDepartmentId(), SchoolStaffMember_.departmentId)
                        : null,
                    criteria.getEmail() != null ? buildStringSpecification(criteria.getEmail(), SchoolStaffMember_.email) : null,
                    criteria.getPhone() != null ? buildStringSpecification(criteria.getPhone(), SchoolStaffMember_.phone) : null,
                    criteria.getProfileImageUrl() != null
                        ? buildStringSpecification(criteria.getProfileImageUrl(), SchoolStaffMember_.profileImageUrl)
                        : null,
                    criteria.getBio() != null ? buildStringSpecification(criteria.getBio(), SchoolStaffMember_.bio) : null,
                    criteria.getPriorityOrder() != null
                        ? buildRangeSpecification(criteria.getPriorityOrder(), SchoolStaffMember_.priorityOrder)
                        : null,
                    criteria.getIsActive() != null ? buildSpecification(criteria.getIsActive(), SchoolStaffMember_.isActive) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
