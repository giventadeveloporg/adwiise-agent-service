package com.adwiiseagent.service;

import com.adwiiseagent.domain.LeagueMatch;
import com.adwiiseagent.repository.LeagueMatchRepository;
import com.adwiiseagent.service.criteria.AttributeSpecifications;
import com.adwiiseagent.service.criteria.LeagueMatchCriteria;
import com.adwiiseagent.service.dto.LeagueMatchDTO;
import com.adwiiseagent.service.mapper.LeagueMatchMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class LeagueMatchQueryService extends QueryService<LeagueMatch> {

    private final LeagueMatchRepository repository;
    private final LeagueMatchMapper mapper;

    public LeagueMatchQueryService(LeagueMatchRepository repository, LeagueMatchMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<LeagueMatchDTO> findByCriteria(LeagueMatchCriteria criteria, Pageable page) {
        return repository.findAll(createSpecification(criteria), page).map(mapper::toDto);
    }

    public long countByCriteria(LeagueMatchCriteria criteria) {
        return repository.count(createSpecification(criteria));
    }

    protected Specification<LeagueMatch> createSpecification(LeagueMatchCriteria criteria) {
        if (criteria == null) {
            return Specification.where(null);
        }
        return Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? AttributeSpecifications.longEquals("id", criteria.getId()) : null,
                    criteria.getTenantId() != null ? AttributeSpecifications.stringMatch("tenantId", criteria.getTenantId()) : null,
                    criteria.getLeagueId() != null ? AttributeSpecifications.longEquals("leagueId", criteria.getLeagueId()) : null,
                    criteria.getHomeClubId() != null ? AttributeSpecifications.longEquals("homeClubId", criteria.getHomeClubId()) : null,
                    criteria.getAwayClubId() != null ? AttributeSpecifications.longEquals("awayClubId", criteria.getAwayClubId()) : null,
                    criteria.getVenue() != null ? AttributeSpecifications.stringMatch("venue", criteria.getVenue()) : null,
                    criteria.getHomeScore() != null ? AttributeSpecifications.integerEquals("homeScore", criteria.getHomeScore()) : null,
                    criteria.getAwayScore() != null ? AttributeSpecifications.integerEquals("awayScore", criteria.getAwayScore()) : null,
                    criteria.getStatus() != null ? AttributeSpecifications.stringMatch("status", criteria.getStatus()) : null,
                    criteria.getNotes() != null ? AttributeSpecifications.stringMatch("notes", criteria.getNotes()) : null
        );
    }
}
