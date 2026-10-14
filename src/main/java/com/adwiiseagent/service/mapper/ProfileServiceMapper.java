package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileService;
import com.adwiiseagent.service.dto.ProfileServiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileServiceMapper extends EntityMapper<ProfileServiceDTO, ProfileService> {}
