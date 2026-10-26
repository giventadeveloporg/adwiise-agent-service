package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TeamMember;
import com.adwiiseagent.service.dto.TeamMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamMember} and its DTO {@link TeamMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMemberMapper extends EntityMapper<TeamMemberDTO, TeamMember> {
    @Override
    @Mapping(target = "sportCategoryIds", ignore = true)
    @Mapping(target = "sportLabels", ignore = true)
    TeamMemberDTO toDto(TeamMember entity);
}
