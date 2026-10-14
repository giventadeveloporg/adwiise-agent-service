package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.GasStationDailyMetricsDTO;
import java.util.Optional;

public interface GasStationDailyMetricsService {
    GasStationDailyMetricsDTO save(GasStationDailyMetricsDTO gasStationDailyMetricsDTO);
    GasStationDailyMetricsDTO update(GasStationDailyMetricsDTO gasStationDailyMetricsDTO);
    Optional<GasStationDailyMetricsDTO> partialUpdate(GasStationDailyMetricsDTO gasStationDailyMetricsDTO);
    Optional<GasStationDailyMetricsDTO> findOne(Long id);
    void delete(Long id);
}
