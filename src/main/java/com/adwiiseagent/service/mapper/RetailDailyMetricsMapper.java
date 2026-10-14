package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailDailyMetrics;
import com.adwiiseagent.service.dto.RetailDailyMetricsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailDailyMetricsMapper extends EntityMapper<RetailDailyMetricsDTO, RetailDailyMetrics> {}
