package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SportsCategory;
import com.adwiiseagent.service.dto.SportsCategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SportsCategoryMapper extends EntityMapper<SportsCategoryDTO, SportsCategory> {}
