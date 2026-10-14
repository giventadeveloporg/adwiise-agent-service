package com.adwiiseagent.repository;

import com.adwiiseagent.domain.GasStationIntegration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationIntegrationRepository
    extends JpaRepository<GasStationIntegration, Long>, JpaSpecificationExecutor<GasStationIntegration> {}
