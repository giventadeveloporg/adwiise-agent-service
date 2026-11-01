package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RecommendationCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationCategoryRepository extends JpaRepository<RecommendationCategory, Long> {
    Optional<RecommendationCategory> findByCode(String code);

    Optional<RecommendationCategory> findByCodeAndIsActiveTrue(String code);

    List<RecommendationCategory> findAllByOrderBySortOrderAsc();
}
