package com.adwiiseagent.service;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RecommendationCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RecommendationCategoryLookup {

    private final RecommendationCategoryRepository repository;

    public RecommendationCategoryLookup(RecommendationCategoryRepository repository) {
        this.repository = repository;
    }

    public void requireActive(String code) {
        if (code == null || code.isBlank()) {
            throw new BadRequestAlertException("category is required", "retailRecommendation", "categoryrequired");
        }
        String normalized = code.trim().toUpperCase();
        if (repository.findByCodeAndIsActiveTrue(normalized).isEmpty()) {
            throw new BadRequestAlertException("Unknown recommendation category: " + normalized, "retailRecommendation", "categoryunknown");
        }
    }
}
