package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RetailRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailRecommendationRepository
    extends JpaRepository<RetailRecommendation, Long>, JpaSpecificationExecutor<RetailRecommendation> {}
