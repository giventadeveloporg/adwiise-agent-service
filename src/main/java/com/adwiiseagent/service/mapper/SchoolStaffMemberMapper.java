package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolStaffMember;
import com.adwiiseagent.service.dto.SchoolStaffMemberDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolStaffMemberMapper extends EntityMapper<SchoolStaffMemberDTO, SchoolStaffMember> {}
