package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.TeamMemberAchievementDTO;
import java.util.Optional;

public interface TeamMemberAchievementService {
    TeamMemberAchievementDTO save(TeamMemberAchievementDTO teamMemberAchievementDTO);

    TeamMemberAchievementDTO update(TeamMemberAchievementDTO teamMemberAchievementDTO);

    Optional<TeamMemberAchievementDTO> partialUpdate(TeamMemberAchievementDTO teamMemberAchievementDTO);

    Optional<TeamMemberAchievementDTO> findOne(Long id);

    void delete(Long id);
}
