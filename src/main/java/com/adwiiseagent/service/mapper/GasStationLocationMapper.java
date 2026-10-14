package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.GasStationLocation;
import com.adwiiseagent.service.dto.GasStationLocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GasStationLocationMapper extends EntityMapper<GasStationLocationDTO, GasStationLocation> {}
