package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.GasStationIntegration;
import com.adwiiseagent.service.dto.GasStationIntegrationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GasStationIntegrationMapper extends EntityMapper<GasStationIntegrationDTO, GasStationIntegration> {}
