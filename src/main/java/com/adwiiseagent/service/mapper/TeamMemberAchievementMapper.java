package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TeamMemberAchievement;
import com.adwiiseagent.service.dto.TeamMemberAchievementDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMemberAchievementMapper extends EntityMapper<TeamMemberAchievementDTO, TeamMemberAchievement> {}
