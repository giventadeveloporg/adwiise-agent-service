package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.AchievementCategory;
import com.adwiiseagent.service.dto.AchievementCategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AchievementCategoryMapper extends EntityMapper<AchievementCategoryDTO, AchievementCategory> {}
