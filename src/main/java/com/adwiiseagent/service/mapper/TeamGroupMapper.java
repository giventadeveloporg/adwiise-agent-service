package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TeamGroup;
import com.adwiiseagent.service.dto.TeamGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamGroup} and its DTO {@link TeamGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamGroupMapper extends EntityMapper<TeamGroupDTO, TeamGroup> {}
