package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TeamType;
import com.adwiiseagent.service.dto.TeamTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamTypeMapper extends EntityMapper<TeamTypeDTO, TeamType> {}
