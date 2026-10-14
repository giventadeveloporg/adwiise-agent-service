package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.GasStationDailyMetrics;
import com.adwiiseagent.service.dto.GasStationDailyMetricsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GasStationDailyMetricsMapper extends EntityMapper<GasStationDailyMetricsDTO, GasStationDailyMetrics> {}
