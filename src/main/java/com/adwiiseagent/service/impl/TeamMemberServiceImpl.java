package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TeamMember;
import com.adwiiseagent.repository.TeamMemberRepository;
import com.adwiiseagent.service.RosterCatalogLookup;
import com.adwiiseagent.service.TeamMemberService;
import com.adwiiseagent.service.dto.TeamMemberDTO;
import com.adwiiseagent.service.mapper.TeamMemberMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.TeamMember}.
 */
@Service
@Transactional
public class TeamMemberServiceImpl implements TeamMemberService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamMemberServiceImpl.class);

    private final TeamMemberRepository teamMemberRepository;

    private final TeamMemberMapper teamMemberMapper;

    private final RosterCatalogLookup rosterCatalogLookup;

    public TeamMemberServiceImpl(
        TeamMemberRepository teamMemberRepository,
        TeamMemberMapper teamMemberMapper,
        RosterCatalogLookup rosterCatalogLookup
    ) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamMemberMapper = teamMemberMapper;
        this.rosterCatalogLookup = rosterCatalogLookup;
    }

    @Override
    public TeamMemberDTO save(TeamMemberDTO teamMemberDTO) {
        LOG.debug("Request to save TeamMember : {}", teamMemberDTO);
        TeamMember teamMember = teamMemberMapper.toEntity(teamMemberDTO);

        if (teamMember.getId() != null) {
            LOG.warn("TeamMember has ID {} set during create operation. Clearing ID to force sequence generation.", teamMember.getId());
            teamMember.setId(null);
        }

        teamMember = teamMemberRepository.save(teamMember);
        return finish(teamMember, teamMemberDTO.getSportCategoryIds());
    }

    @Override
    public TeamMemberDTO update(TeamMemberDTO teamMemberDTO) {
        LOG.debug("Request to update TeamMember : {}", teamMemberDTO);
        TeamMember teamMember = teamMemberMapper.toEntity(teamMemberDTO);
        teamMember = teamMemberRepository.save(teamMember);
        return finish(teamMember, teamMemberDTO.getSportCategoryIds());
    }

    @Override
    public Optional<TeamMemberDTO> partialUpdate(TeamMemberDTO teamMemberDTO) {
        LOG.debug("Request to partially update TeamMember : {}", teamMemberDTO);

        return teamMemberRepository
            .findById(teamMemberDTO.getId())
            .map(existingTeamMember -> {
                teamMemberMapper.partialUpdate(existingTeamMember, teamMemberDTO);
                return existingTeamMember;
            })
            .map(teamMemberRepository::save)
            .map(saved -> finish(saved, teamMemberDTO.getSportCategoryIds()));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamMemberDTO> findOne(Long id) {
        LOG.debug("Request to get TeamMember : {}", id);
        return teamMemberRepository.findById(id).map(teamMemberMapper::toDto).map(dto -> {
            rosterCatalogLookup.attachSports(List.of(dto));
            return dto;
        });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TeamMember : {}", id);
        teamMemberRepository.deleteById(id);
    }

    private TeamMemberDTO finish(TeamMember saved, List<Long> sportCategoryIds) {
        if (sportCategoryIds != null) {
            rosterCatalogLookup.replaceMemberSports(saved, sportCategoryIds);
        }
        TeamMemberDTO dto = teamMemberMapper.toDto(saved);
        rosterCatalogLookup.attachSports(List.of(dto));
        return dto;
    }
}
