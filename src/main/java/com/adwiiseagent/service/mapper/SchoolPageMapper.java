package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolPage;
import com.adwiiseagent.service.dto.SchoolPageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolPageMapper extends EntityMapper<SchoolPageDTO, SchoolPage> {}
