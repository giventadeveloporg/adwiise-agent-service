package com.adwiiseagent.repository;

import com.adwiiseagent.domain.GasStationLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationLocationRepository
    extends JpaRepository<GasStationLocation, Long>, JpaSpecificationExecutor<GasStationLocation> {}
