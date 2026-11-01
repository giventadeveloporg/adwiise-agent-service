package com.adwiiseagent.service;

import com.adwiiseagent.domain.TeamGroupMembership;
import com.adwiiseagent.repository.TeamGroupMembershipRepository;
import com.adwiiseagent.service.criteria.AttributeSpecifications;
import com.adwiiseagent.service.criteria.TeamGroupMembershipCriteria;
import com.adwiiseagent.service.dto.TeamGroupMembershipDTO;
import com.adwiiseagent.service.mapper.TeamGroupMembershipMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class TeamGroupMembershipQueryService extends QueryService<TeamGroupMembership> {

    private final TeamGroupMembershipRepository repository;
    private final TeamGroupMembershipMapper mapper;

    public TeamGroupMembershipQueryService(TeamGroupMembershipRepository repository, TeamGroupMembershipMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<TeamGroupMembershipDTO> findByCriteria(TeamGroupMembershipCriteria criteria, Pageable page) {
        return repository.findAll(createSpecification(criteria), page).map(mapper::toDto);
    }

    public long countByCriteria(TeamGroupMembershipCriteria criteria) {
        return repository.count(createSpecification(criteria));
    }

    protected Specification<TeamGroupMembership> createSpecification(TeamGroupMembershipCriteria criteria) {
        if (criteria == null) {
            return Specification.where(null);
        }
        return Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? AttributeSpecifications.longEquals("id", criteria.getId()) : null,
                    criteria.getTenantId() != null ? AttributeSpecifications.stringMatch("tenantId", criteria.getTenantId()) : null,
                    criteria.getTeamGroupId() != null ? AttributeSpecifications.longEquals("teamGroupId", criteria.getTeamGroupId()) : null,
                    criteria.getTeamMemberId() != null ? AttributeSpecifications.longEquals("teamMemberId", criteria.getTeamMemberId()) : null,
                    criteria.getJerseyNumber() != null ? AttributeSpecifications.integerEquals("jerseyNumber", criteria.getJerseyNumber()) : null,
                    criteria.getPosition() != null ? AttributeSpecifications.stringMatch("position", criteria.getPosition()) : null,
                    criteria.getIsActive() != null ? AttributeSpecifications.booleanEquals("isActive", criteria.getIsActive()) : null
        );
    }
}
