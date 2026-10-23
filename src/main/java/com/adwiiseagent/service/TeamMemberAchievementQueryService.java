package com.adwiiseagent.service;

import com.adwiiseagent.domain.TeamMemberAchievement;
import com.adwiiseagent.domain.TeamMemberAchievement_;
import com.adwiiseagent.repository.TeamMemberAchievementRepository;
import com.adwiiseagent.service.criteria.TeamMemberAchievementCriteria;
import com.adwiiseagent.service.dto.TeamMemberAchievementDTO;
import com.adwiiseagent.service.mapper.TeamMemberAchievementMapper;
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
public class TeamMemberAchievementQueryService extends QueryService<TeamMemberAchievement> {

    private static final Logger LOG = LoggerFactory.getLogger(TeamMemberAchievementQueryService.class);

    private final TeamMemberAchievementRepository teamMemberAchievementRepository;

    private final TeamMemberAchievementMapper teamMemberAchievementMapper;

    public TeamMemberAchievementQueryService(
        TeamMemberAchievementRepository teamMemberAchievementRepository,
        TeamMemberAchievementMapper teamMemberAchievementMapper
    ) {
        this.teamMemberAchievementRepository = teamMemberAchievementRepository;
        this.teamMemberAchievementMapper = teamMemberAchievementMapper;
    }

    public Page<TeamMemberAchievementDTO> findByCriteria(TeamMemberAchievementCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        return teamMemberAchievementRepository.findAll(createSpecification(criteria), page).map(teamMemberAchievementMapper::toDto);
    }

    public long countByCriteria(TeamMemberAchievementCriteria criteria) {
        return teamMemberAchievementRepository.count(createSpecification(criteria));
    }

    protected Specification<TeamMemberAchievement> createSpecification(TeamMemberAchievementCriteria criteria) {
        Specification<TeamMemberAchievement> specification = Specification.where(null);
        if (criteria != null) {
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                criteria.getId() != null ? buildRangeSpecification(criteria.getId(), TeamMemberAchievement_.id) : null,
                criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), TeamMemberAchievement_.tenantId) : null,
                criteria.getTeamMemberId() != null
                    ? buildRangeSpecification(criteria.getTeamMemberId(), TeamMemberAchievement_.teamMemberId)
                    : null,
                criteria.getTitle() != null ? buildStringSpecification(criteria.getTitle(), TeamMemberAchievement_.title) : null,
                criteria.getDescription() != null
                    ? buildStringSpecification(criteria.getDescription(), TeamMemberAchievement_.description)
                    : null,
                criteria.getAchievementDate() != null
                    ? buildRangeSpecification(criteria.getAchievementDate(), TeamMemberAchievement_.achievementDate)
                    : null,
                criteria.getCategory() != null ? buildStringSpecification(criteria.getCategory(), TeamMemberAchievement_.category) : null,
                criteria.getIssuer() != null ? buildStringSpecification(criteria.getIssuer(), TeamMemberAchievement_.issuer) : null,
                criteria.getDisplayOrder() != null
                    ? buildRangeSpecification(criteria.getDisplayOrder(), TeamMemberAchievement_.displayOrder)
                    : null,
                criteria.getIsFeatured() != null ? buildSpecification(criteria.getIsFeatured(), TeamMemberAchievement_.isFeatured) : null
            );
        }
        return specification;
    }
}
