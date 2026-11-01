package com.adwiiseagent.service;

import com.adwiiseagent.domain.LeagueStanding;
import com.adwiiseagent.repository.LeagueStandingRepository;
import com.adwiiseagent.service.criteria.AttributeSpecifications;
import com.adwiiseagent.service.criteria.LeagueStandingCriteria;
import com.adwiiseagent.service.dto.LeagueStandingDTO;
import com.adwiiseagent.service.mapper.LeagueStandingMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class LeagueStandingQueryService extends QueryService<LeagueStanding> {

    private final LeagueStandingRepository repository;
    private final LeagueStandingMapper mapper;

    public LeagueStandingQueryService(LeagueStandingRepository repository, LeagueStandingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<LeagueStandingDTO> findByCriteria(LeagueStandingCriteria criteria, Pageable page) {
        return repository.findAll(createSpecification(criteria), page).map(mapper::toDto);
    }

    public long countByCriteria(LeagueStandingCriteria criteria) {
        return repository.count(createSpecification(criteria));
    }

    protected Specification<LeagueStanding> createSpecification(LeagueStandingCriteria criteria) {
        if (criteria == null) {
            return Specification.where(null);
        }
        return Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? AttributeSpecifications.longEquals("id", criteria.getId()) : null,
                    criteria.getTenantId() != null ? AttributeSpecifications.stringMatch("tenantId", criteria.getTenantId()) : null,
                    criteria.getLeagueId() != null ? AttributeSpecifications.longEquals("leagueId", criteria.getLeagueId()) : null,
                    criteria.getLeagueClubId() != null ? AttributeSpecifications.longEquals("leagueClubId", criteria.getLeagueClubId()) : null,
                    criteria.getPlayed() != null ? AttributeSpecifications.integerEquals("played", criteria.getPlayed()) : null,
                    criteria.getWon() != null ? AttributeSpecifications.integerEquals("won", criteria.getWon()) : null,
                    criteria.getDrawn() != null ? AttributeSpecifications.integerEquals("drawn", criteria.getDrawn()) : null,
                    criteria.getLost() != null ? AttributeSpecifications.integerEquals("lost", criteria.getLost()) : null,
                    criteria.getGoalsFor() != null ? AttributeSpecifications.integerEquals("goalsFor", criteria.getGoalsFor()) : null,
                    criteria.getGoalsAgainst() != null ? AttributeSpecifications.integerEquals("goalsAgainst", criteria.getGoalsAgainst()) : null,
                    criteria.getPoints() != null ? AttributeSpecifications.integerEquals("points", criteria.getPoints()) : null,
                    criteria.getPosition() != null ? AttributeSpecifications.integerEquals("position", criteria.getPosition()) : null
        );
    }
}
