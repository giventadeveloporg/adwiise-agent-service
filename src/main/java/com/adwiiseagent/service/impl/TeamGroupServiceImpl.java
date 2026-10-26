package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TeamGroup;
import com.adwiiseagent.repository.TeamGroupRepository;
import com.adwiiseagent.service.RosterCatalogLookup;
import com.adwiiseagent.service.TeamGroupService;
import com.adwiiseagent.service.dto.TeamGroupDTO;
import com.adwiiseagent.service.mapper.TeamGroupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.TeamGroup}.
 */
@Service
@Transactional
public class TeamGroupServiceImpl implements TeamGroupService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamGroupServiceImpl.class);

    private final TeamGroupRepository teamGroupRepository;

    private final TeamGroupMapper teamGroupMapper;

    private final RosterCatalogLookup rosterCatalogLookup;

    public TeamGroupServiceImpl(
        TeamGroupRepository teamGroupRepository,
        TeamGroupMapper teamGroupMapper,
        RosterCatalogLookup rosterCatalogLookup
    ) {
        this.teamGroupRepository = teamGroupRepository;
        this.teamGroupMapper = teamGroupMapper;
        this.rosterCatalogLookup = rosterCatalogLookup;
    }

    @Override
    public TeamGroupDTO save(TeamGroupDTO teamGroupDTO) {
        LOG.debug("Request to save TeamGroup : {}", teamGroupDTO);
        TeamGroup teamGroup = teamGroupMapper.toEntity(teamGroupDTO);

        if (teamGroup.getId() != null) {
            LOG.warn("TeamGroup has ID {} set during create operation. Clearing ID to force sequence generation.", teamGroup.getId());
            teamGroup.setId(null);
        }

        rosterCatalogLookup.requireTeamType(teamGroup.getTeamType(), teamGroup.getTenantId());
        teamGroup = teamGroupRepository.save(teamGroup);
        return teamGroupMapper.toDto(teamGroup);
    }

    @Override
    public TeamGroupDTO update(TeamGroupDTO teamGroupDTO) {
        LOG.debug("Request to update TeamGroup : {}", teamGroupDTO);
        TeamGroup teamGroup = teamGroupMapper.toEntity(teamGroupDTO);
        rosterCatalogLookup.requireTeamType(teamGroup.getTeamType(), teamGroup.getTenantId());
        teamGroup = teamGroupRepository.save(teamGroup);
        return teamGroupMapper.toDto(teamGroup);
    }

    @Override
    public Optional<TeamGroupDTO> partialUpdate(TeamGroupDTO teamGroupDTO) {
        LOG.debug("Request to partially update TeamGroup : {}", teamGroupDTO);

        return teamGroupRepository
            .findById(teamGroupDTO.getId())
            .map(existingTeamGroup -> {
                teamGroupMapper.partialUpdate(existingTeamGroup, teamGroupDTO);
                rosterCatalogLookup.requireTeamType(existingTeamGroup.getTeamType(), existingTeamGroup.getTenantId());
                return existingTeamGroup;
            })
            .map(teamGroupRepository::save)
            .map(teamGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamGroupDTO> findOne(Long id) {
        LOG.debug("Request to get TeamGroup : {}", id);
        return teamGroupRepository.findById(id).map(teamGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TeamGroup : {}", id);
        teamGroupRepository.deleteById(id);
    }
}
