package com.adwiiseagent.repository;

import com.adwiiseagent.domain.GasStationRecommendation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRecommendationRepository
    extends JpaRepository<GasStationRecommendation, Long>, JpaSpecificationExecutor<GasStationRecommendation> {}
