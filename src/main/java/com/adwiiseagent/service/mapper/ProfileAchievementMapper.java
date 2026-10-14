package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileAchievement;
import com.adwiiseagent.service.dto.ProfileAchievementDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileAchievementMapper extends EntityMapper<ProfileAchievementDTO, ProfileAchievement> {}
