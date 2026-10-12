package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailDailyMetrics;
import com.eventsitemanager.service.dto.RetailDailyMetricsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailDailyMetricsMapper extends EntityMapper<RetailDailyMetricsDTO, RetailDailyMetrics> {}
