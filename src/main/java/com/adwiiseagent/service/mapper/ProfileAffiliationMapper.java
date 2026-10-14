package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileAffiliation;
import com.adwiiseagent.service.dto.ProfileAffiliationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileAffiliationMapper extends EntityMapper<ProfileAffiliationDTO, ProfileAffiliation> {}
