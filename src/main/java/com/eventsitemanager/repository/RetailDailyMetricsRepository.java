package com.eventsitemanager.repository;

import com.eventsitemanager.domain.RetailDailyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailDailyMetricsRepository
    extends JpaRepository<RetailDailyMetrics, Long>, JpaSpecificationExecutor<RetailDailyMetrics> {}
