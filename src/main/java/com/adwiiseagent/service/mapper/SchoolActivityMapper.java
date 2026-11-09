package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolActivity;
import com.adwiiseagent.service.dto.SchoolActivityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolActivityMapper extends EntityMapper<SchoolActivityDTO, SchoolActivity> {}
