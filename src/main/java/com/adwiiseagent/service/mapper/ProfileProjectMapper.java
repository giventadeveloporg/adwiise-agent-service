package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileProject;
import com.adwiiseagent.service.dto.ProfileProjectDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileProjectMapper extends EntityMapper<ProfileProjectDTO, ProfileProject> {}
