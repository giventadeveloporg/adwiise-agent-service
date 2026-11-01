package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.League;
import com.adwiiseagent.repository.LeagueRepository;
import com.adwiiseagent.service.LeagueService;
import com.adwiiseagent.service.dto.LeagueDTO;
import com.adwiiseagent.service.mapper.LeagueMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;
    private final LeagueMapper mapper;

    public LeagueServiceImpl(LeagueRepository repository, LeagueMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private String slugify(String value) {
        String slug = value == null ? "" : value.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
        return slug.isBlank() ? "league" : slug;
    }

    private void prepare(League entity) {
        if (entity.getSlug() == null || entity.getSlug().isBlank()) {
            entity.setSlug(slugify(entity.getName()));
        } else {
            entity.setSlug(slugify(entity.getSlug()));
        }
        if (entity.getStatus() == null || entity.getStatus().isBlank()) {
            entity.setStatus("DRAFT");
        }
        if (entity.getStandingsSource() == null || entity.getStandingsSource().isBlank()) {
            entity.setStandingsSource("CALCULATED");
        }
        String base = entity.getSlug();
        String candidate = base;
        int n = 2;
        while (true) {
            var existing = repository.findOneByTenantIdAndSlug(entity.getTenantId(), candidate);
            boolean slugAvailable = existing.map(found -> found.getId().equals(entity.getId())).orElse(true);
            if (slugAvailable) {
                entity.setSlug(candidate);
                return;
            }
            candidate = base + "-" + n;
            n++;
        }
    }

    @Override
    public LeagueDTO save(LeagueDTO dto) {
        League entity = mapper.toEntity(dto);
        if (entity.getId() != null) {
            entity.setId(null);
        }
        prepare(entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public LeagueDTO update(LeagueDTO dto) {
        League entity = mapper.toEntity(dto);
        prepare(entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public Optional<LeagueDTO> partialUpdate(LeagueDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                mapper.partialUpdate(existing, dto);
                prepare(existing);
                return existing;
            })
            .map(repository::save)
            .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeagueDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
