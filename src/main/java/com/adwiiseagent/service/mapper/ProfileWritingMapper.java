package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileWriting;
import com.adwiiseagent.service.dto.ProfileWritingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileWritingMapper extends EntityMapper<ProfileWritingDTO, ProfileWriting> {}
