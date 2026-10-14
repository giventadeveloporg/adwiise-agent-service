package com.adwiiseagent.repository;

import com.adwiiseagent.domain.GasStationDailyMetrics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationDailyMetricsRepository
    extends JpaRepository<GasStationDailyMetrics, Long>, JpaSpecificationExecutor<GasStationDailyMetrics> {}
