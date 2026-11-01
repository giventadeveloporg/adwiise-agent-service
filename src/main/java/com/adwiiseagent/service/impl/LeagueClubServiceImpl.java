package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.LeagueClub;
import com.adwiiseagent.repository.LeagueClubRepository;
import com.adwiiseagent.domain.League;
import com.adwiiseagent.repository.LeagueRepository;
import com.adwiiseagent.service.LeagueClubService;
import com.adwiiseagent.service.dto.LeagueClubDTO;
import com.adwiiseagent.service.mapper.LeagueClubMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LeagueClubServiceImpl implements LeagueClubService {

    private final LeagueClubRepository repository;
    private final LeagueClubMapper mapper;
    private final LeagueRepository leagueRepository;

    public LeagueClubServiceImpl(LeagueClubRepository repository, LeagueClubMapper mapper, LeagueRepository leagueRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.leagueRepository = leagueRepository;
    }

    private void requireSameTenant(String tenantId, Long leagueId) {
        League league = leagueRepository.findById(leagueId).orElseThrow(() ->
            new com.adwiiseagent.errors.BadRequestAlertException("League not found", "league", "leaguenotfound")
        );
        if (tenantId == null || !tenantId.equals(league.getTenantId())) {
            throw new com.adwiiseagent.errors.BadRequestAlertException("League is not in this tenant", "league", "tenantmismatch");
        }
    }

    @Override
    public LeagueClubDTO save(LeagueClubDTO dto) {
        LeagueClub entity = mapper.toEntity(dto);
        if (entity.getId() != null) {
            entity.setId(null);
        }

        requireSameTenant(entity.getTenantId(), entity.getLeagueId());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public LeagueClubDTO update(LeagueClubDTO dto) {
        LeagueClub entity = mapper.toEntity(dto);

        requireSameTenant(entity.getTenantId(), entity.getLeagueId());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public Optional<LeagueClubDTO> partialUpdate(LeagueClubDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                mapper.partialUpdate(existing, dto);
                requireSameTenant(existing.getTenantId(), existing.getLeagueId());
                return existing;
            })
            .map(repository::save)
            .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeagueClubDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
