package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TeamGroupMembership;
import com.adwiiseagent.service.dto.TeamGroupMembershipDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamGroupMembershipMapper extends EntityMapper<TeamGroupMembershipDTO, TeamGroupMembership> {}
