package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.LeagueMatch;
import com.adwiiseagent.repository.LeagueMatchRepository;
import com.adwiiseagent.domain.League;
import com.adwiiseagent.domain.LeagueClub;
import com.adwiiseagent.repository.LeagueClubRepository;
import com.adwiiseagent.repository.LeagueRepository;
import com.adwiiseagent.service.LeagueMatchService;
import com.adwiiseagent.service.dto.LeagueMatchDTO;
import com.adwiiseagent.service.mapper.LeagueMatchMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LeagueMatchServiceImpl implements LeagueMatchService {

    private final LeagueMatchRepository repository;
    private final LeagueMatchMapper mapper;
    private final LeagueRepository leagueRepository;
    private final LeagueClubRepository leagueClubRepository;

    public LeagueMatchServiceImpl(LeagueMatchRepository repository, LeagueMatchMapper mapper, LeagueRepository leagueRepository, LeagueClubRepository leagueClubRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.leagueRepository = leagueRepository;
        this.leagueClubRepository = leagueClubRepository;
    }

    private void validate(LeagueMatch match) {
        if (match.getTenantId() == null || match.getTenantId().isBlank()) {
            throw new com.adwiiseagent.errors.BadRequestAlertException("tenantId is required", "leagueMatch", "tenantidrequired");
        }
        if (match.getHomeClubId() != null && match.getHomeClubId().equals(match.getAwayClubId())) {
            throw new com.adwiiseagent.errors.BadRequestAlertException("Home and away clubs must differ", "leagueMatch", "sameclub");
        }
        League league = leagueRepository.findById(match.getLeagueId()).orElseThrow(() ->
            new com.adwiiseagent.errors.BadRequestAlertException("League not found", "leagueMatch", "leaguenotfound")
        );
        if (!league.getTenantId().equals(match.getTenantId())) {
            throw new com.adwiiseagent.errors.BadRequestAlertException("League is not in this tenant", "leagueMatch", "tenantmismatch");
        }
        requireClub(match.getHomeClubId(), match);
        requireClub(match.getAwayClubId(), match);
        if ("PLAYED".equals(match.getStatus()) && (match.getHomeScore() == null || match.getAwayScore() == null)) {
            throw new com.adwiiseagent.errors.BadRequestAlertException("Played matches need both scores", "leagueMatch", "scoresrequired");
        }
    }

    private void requireClub(Long clubId, LeagueMatch match) {
        LeagueClub club = leagueClubRepository.findById(clubId).orElseThrow(() ->
            new com.adwiiseagent.errors.BadRequestAlertException("Club not found", "leagueMatch", "clubnotfound")
        );
        if (!club.getLeagueId().equals(match.getLeagueId()) || !club.getTenantId().equals(match.getTenantId())) {
            throw new com.adwiiseagent.errors.BadRequestAlertException("Club is not in this league", "leagueMatch", "clubnotinleague");
        }
    }

    @Override
    public LeagueMatchDTO save(LeagueMatchDTO dto) {
        LeagueMatch entity = mapper.toEntity(dto);
        if (entity.getId() != null) {
            entity.setId(null);
        }

        validate(entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public LeagueMatchDTO update(LeagueMatchDTO dto) {
        LeagueMatch entity = mapper.toEntity(dto);

        validate(entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public Optional<LeagueMatchDTO> partialUpdate(LeagueMatchDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                mapper.partialUpdate(existing, dto);
                validate(existing);
                return existing;
            })
            .map(repository::save)
            .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeagueMatchDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
