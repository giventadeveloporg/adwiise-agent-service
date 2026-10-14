package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.PublicProfile;
import com.adwiiseagent.service.dto.PublicProfileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicProfileMapper extends EntityMapper<PublicProfileDTO, PublicProfile> {}
