package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ExecutiveCommitteeTeamMember;
import com.adwiiseagent.service.dto.ExecutiveCommitteeTeamMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExecutiveCommitteeTeamMember} and its DTO {@link ExecutiveCommitteeTeamMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExecutiveCommitteeTeamMemberMapper extends EntityMapper<ExecutiveCommitteeTeamMemberDTO, ExecutiveCommitteeTeamMember> {}
