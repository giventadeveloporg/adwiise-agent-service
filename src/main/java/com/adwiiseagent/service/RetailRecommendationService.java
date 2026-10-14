package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.RetailRecommendationDTO;
import java.util.Optional;

public interface RetailRecommendationService {
    RetailRecommendationDTO save(RetailRecommendationDTO dto);
    RetailRecommendationDTO update(RetailRecommendationDTO dto);
    Optional<RetailRecommendationDTO> partialUpdate(RetailRecommendationDTO dto);
    Optional<RetailRecommendationDTO> findOne(Long id);
    void delete(Long id);
}
