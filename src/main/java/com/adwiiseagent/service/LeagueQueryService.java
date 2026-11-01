package com.adwiiseagent.service;

import com.adwiiseagent.domain.League;
import com.adwiiseagent.repository.LeagueRepository;
import com.adwiiseagent.service.criteria.AttributeSpecifications;
import com.adwiiseagent.service.criteria.LeagueCriteria;
import com.adwiiseagent.service.dto.LeagueDTO;
import com.adwiiseagent.service.mapper.LeagueMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class LeagueQueryService extends QueryService<League> {

    private final LeagueRepository repository;
    private final LeagueMapper mapper;

    public LeagueQueryService(LeagueRepository repository, LeagueMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<LeagueDTO> findByCriteria(LeagueCriteria criteria, Pageable page) {
        return repository.findAll(createSpecification(criteria), page).map(mapper::toDto);
    }

    public long countByCriteria(LeagueCriteria criteria) {
        return repository.count(createSpecification(criteria));
    }

    protected Specification<League> createSpecification(LeagueCriteria criteria) {
        if (criteria == null) {
            return Specification.where(null);
        }
        return Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? AttributeSpecifications.longEquals("id", criteria.getId()) : null,
                    criteria.getTenantId() != null ? AttributeSpecifications.stringMatch("tenantId", criteria.getTenantId()) : null,
                    criteria.getName() != null ? AttributeSpecifications.stringMatch("name", criteria.getName()) : null,
                    criteria.getSlug() != null ? AttributeSpecifications.stringMatch("slug", criteria.getSlug()) : null,
                    criteria.getSeasonLabel() != null ? AttributeSpecifications.stringMatch("seasonLabel", criteria.getSeasonLabel()) : null,
                    criteria.getDescription() != null ? AttributeSpecifications.stringMatch("description", criteria.getDescription()) : null,
                    criteria.getLogoUrl() != null ? AttributeSpecifications.stringMatch("logoUrl", criteria.getLogoUrl()) : null,
                    criteria.getStatus() != null ? AttributeSpecifications.stringMatch("status", criteria.getStatus()) : null,
                    criteria.getIsPublished() != null ? AttributeSpecifications.booleanEquals("isPublished", criteria.getIsPublished()) : null,
                    criteria.getShowOnHomepage() != null ? AttributeSpecifications.booleanEquals("showOnHomepage", criteria.getShowOnHomepage()) : null,
                    criteria.getHomepageDisplayOrder() != null ? AttributeSpecifications.integerEquals("homepageDisplayOrder", criteria.getHomepageDisplayOrder()) : null,
                    criteria.getStandingsSource() != null ? AttributeSpecifications.stringMatch("standingsSource", criteria.getStandingsSource()) : null,
                    criteria.getPointsForWin() != null ? AttributeSpecifications.integerEquals("pointsForWin", criteria.getPointsForWin()) : null,
                    criteria.getPointsForDraw() != null ? AttributeSpecifications.integerEquals("pointsForDraw", criteria.getPointsForDraw()) : null,
                    criteria.getPointsForLoss() != null ? AttributeSpecifications.integerEquals("pointsForLoss", criteria.getPointsForLoss()) : null
        );
    }
}
