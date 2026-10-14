package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileAudienceContact;
import com.adwiiseagent.service.dto.ProfileAudienceContactDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileAudienceContactMapper extends EntityMapper<ProfileAudienceContactDTO, ProfileAudienceContact> {}
