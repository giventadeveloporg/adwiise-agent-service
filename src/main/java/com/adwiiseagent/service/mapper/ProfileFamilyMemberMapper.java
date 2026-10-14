package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileFamilyMember;
import com.adwiiseagent.service.dto.ProfileFamilyMemberDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileFamilyMemberMapper extends EntityMapper<ProfileFamilyMemberDTO, ProfileFamilyMember> {}
