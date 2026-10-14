package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.RetailDailyMetricsDTO;
import java.util.Optional;

public interface RetailDailyMetricsService {
    RetailDailyMetricsDTO save(RetailDailyMetricsDTO dto);
    RetailDailyMetricsDTO update(RetailDailyMetricsDTO dto);
    Optional<RetailDailyMetricsDTO> partialUpdate(RetailDailyMetricsDTO dto);
    Optional<RetailDailyMetricsDTO> findOne(Long id);
    void delete(Long id);
}
