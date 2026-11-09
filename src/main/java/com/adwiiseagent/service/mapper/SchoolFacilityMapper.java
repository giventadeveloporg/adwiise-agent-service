package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolFacility;
import com.adwiiseagent.service.dto.SchoolFacilityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolFacilityMapper extends EntityMapper<SchoolFacilityDTO, SchoolFacility> {}
