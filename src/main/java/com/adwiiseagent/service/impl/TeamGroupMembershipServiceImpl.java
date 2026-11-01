package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TeamGroupMembership;
import com.adwiiseagent.repository.TeamGroupMembershipRepository;
import com.adwiiseagent.service.TeamGroupMembershipService;
import com.adwiiseagent.service.dto.TeamGroupMembershipDTO;
import com.adwiiseagent.service.mapper.TeamGroupMembershipMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamGroupMembershipServiceImpl implements TeamGroupMembershipService {

    private final TeamGroupMembershipRepository repository;
    private final TeamGroupMembershipMapper mapper;
    private final com.adwiiseagent.repository.TeamGroupRepository teamGroupRepository;
    private final com.adwiiseagent.repository.TeamMemberRepository teamMemberRepository;

    public TeamGroupMembershipServiceImpl(TeamGroupMembershipRepository repository, TeamGroupMembershipMapper mapper, com.adwiiseagent.repository.TeamGroupRepository teamGroupRepository, com.adwiiseagent.repository.TeamMemberRepository teamMemberRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.teamGroupRepository = teamGroupRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    private void validateMembership(TeamGroupMembership membership) {
        var group = teamGroupRepository.findById(membership.getTeamGroupId()).orElseThrow(() ->
            new com.adwiiseagent.errors.BadRequestAlertException("Squad not found", "teamGroupMembership", "groupnotfound")
        );
        var member = teamMemberRepository.findById(membership.getTeamMemberId()).orElseThrow(() ->
            new com.adwiiseagent.errors.BadRequestAlertException("Member not found", "teamGroupMembership", "membernotfound")
        );
        if (!group.getTenantId().equals(membership.getTenantId()) || !member.getTenantId().equals(membership.getTenantId())) {
            throw new com.adwiiseagent.errors.BadRequestAlertException("Squad and member must belong to this tenant", "teamGroupMembership", "tenantmismatch");
        }
    }

    @Override
    public TeamGroupMembershipDTO save(TeamGroupMembershipDTO dto) {
        TeamGroupMembership entity = mapper.toEntity(dto);
        if (entity.getId() != null) {
            entity.setId(null);
        }

        validateMembership(entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public TeamGroupMembershipDTO update(TeamGroupMembershipDTO dto) {
        TeamGroupMembership entity = mapper.toEntity(dto);

        validateMembership(entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public Optional<TeamGroupMembershipDTO> partialUpdate(TeamGroupMembershipDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                mapper.partialUpdate(existing, dto);
                validateMembership(existing);
                return existing;
            })
            .map(repository::save)
            .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamGroupMembershipDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
