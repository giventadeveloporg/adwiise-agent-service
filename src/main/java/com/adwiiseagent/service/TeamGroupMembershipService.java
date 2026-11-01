package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.TeamGroupMembershipDTO;
import java.util.Optional;

public interface TeamGroupMembershipService {
    TeamGroupMembershipDTO save(TeamGroupMembershipDTO dto);
    TeamGroupMembershipDTO update(TeamGroupMembershipDTO dto);
    Optional<TeamGroupMembershipDTO> partialUpdate(TeamGroupMembershipDTO dto);
    Optional<TeamGroupMembershipDTO> findOne(Long id);
    void delete(Long id);
}
