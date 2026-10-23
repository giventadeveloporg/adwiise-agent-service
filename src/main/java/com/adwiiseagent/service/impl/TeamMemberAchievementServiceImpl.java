package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TeamMemberAchievement;
import com.adwiiseagent.repository.TeamMemberAchievementRepository;
import com.adwiiseagent.service.TeamMemberAchievementService;
import com.adwiiseagent.service.dto.TeamMemberAchievementDTO;
import com.adwiiseagent.service.mapper.TeamMemberAchievementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamMemberAchievementServiceImpl implements TeamMemberAchievementService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamMemberAchievementServiceImpl.class);

    private final TeamMemberAchievementRepository teamMemberAchievementRepository;

    private final TeamMemberAchievementMapper teamMemberAchievementMapper;

    public TeamMemberAchievementServiceImpl(
        TeamMemberAchievementRepository teamMemberAchievementRepository,
        TeamMemberAchievementMapper teamMemberAchievementMapper
    ) {
        this.teamMemberAchievementRepository = teamMemberAchievementRepository;
        this.teamMemberAchievementMapper = teamMemberAchievementMapper;
    }

    @Override
    public TeamMemberAchievementDTO save(TeamMemberAchievementDTO teamMemberAchievementDTO) {
        LOG.debug("Request to save TeamMemberAchievement : {}", teamMemberAchievementDTO);
        TeamMemberAchievement entity = teamMemberAchievementMapper.toEntity(teamMemberAchievementDTO);
        if (entity.getId() != null) {
            entity.setId(null);
        }
        if (entity.getIsFeatured() == null) {
            entity.setIsFeatured(false);
        }
        entity = teamMemberAchievementRepository.save(entity);
        return teamMemberAchievementMapper.toDto(entity);
    }

    @Override
    public TeamMemberAchievementDTO update(TeamMemberAchievementDTO teamMemberAchievementDTO) {
        LOG.debug("Request to update TeamMemberAchievement : {}", teamMemberAchievementDTO);
        TeamMemberAchievement entity = teamMemberAchievementMapper.toEntity(teamMemberAchievementDTO);
        entity = teamMemberAchievementRepository.save(entity);
        return teamMemberAchievementMapper.toDto(entity);
    }

    @Override
    public Optional<TeamMemberAchievementDTO> partialUpdate(TeamMemberAchievementDTO teamMemberAchievementDTO) {
        LOG.debug("Request to partially update TeamMemberAchievement : {}", teamMemberAchievementDTO);
        return teamMemberAchievementRepository
            .findById(teamMemberAchievementDTO.getId())
            .map(existing -> {
                teamMemberAchievementMapper.partialUpdate(existing, teamMemberAchievementDTO);
                return existing;
            })
            .map(teamMemberAchievementRepository::save)
            .map(teamMemberAchievementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamMemberAchievementDTO> findOne(Long id) {
        return teamMemberAchievementRepository.findById(id).map(teamMemberAchievementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        teamMemberAchievementRepository.deleteById(id);
    }
}
