package com.adwiiseagent.service;

import com.adwiiseagent.domain.LeagueClub;
import com.adwiiseagent.repository.LeagueClubRepository;
import com.adwiiseagent.service.criteria.AttributeSpecifications;
import com.adwiiseagent.service.criteria.LeagueClubCriteria;
import com.adwiiseagent.service.dto.LeagueClubDTO;
import com.adwiiseagent.service.mapper.LeagueClubMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class LeagueClubQueryService extends QueryService<LeagueClub> {

    private final LeagueClubRepository repository;
    private final LeagueClubMapper mapper;

    public LeagueClubQueryService(LeagueClubRepository repository, LeagueClubMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<LeagueClubDTO> findByCriteria(LeagueClubCriteria criteria, Pageable page) {
        return repository.findAll(createSpecification(criteria), page).map(mapper::toDto);
    }

    public long countByCriteria(LeagueClubCriteria criteria) {
        return repository.count(createSpecification(criteria));
    }

    protected Specification<LeagueClub> createSpecification(LeagueClubCriteria criteria) {
        if (criteria == null) {
            return Specification.where(null);
        }
        return Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? AttributeSpecifications.longEquals("id", criteria.getId()) : null,
                    criteria.getTenantId() != null ? AttributeSpecifications.stringMatch("tenantId", criteria.getTenantId()) : null,
                    criteria.getLeagueId() != null ? AttributeSpecifications.longEquals("leagueId", criteria.getLeagueId()) : null,
                    criteria.getName() != null ? AttributeSpecifications.stringMatch("name", criteria.getName()) : null,
                    criteria.getShortName() != null ? AttributeSpecifications.stringMatch("shortName", criteria.getShortName()) : null,
                    criteria.getLogoUrl() != null ? AttributeSpecifications.stringMatch("logoUrl", criteria.getLogoUrl()) : null,
                    criteria.getIsHomeClub() != null ? AttributeSpecifications.booleanEquals("isHomeClub", criteria.getIsHomeClub()) : null,
                    criteria.getDisplayOrder() != null ? AttributeSpecifications.integerEquals("displayOrder", criteria.getDisplayOrder()) : null
        );
    }
}
