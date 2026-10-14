package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailRecommendation;
import com.adwiiseagent.service.dto.RetailRecommendationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailRecommendationMapper extends EntityMapper<RetailRecommendationDTO, RetailRecommendation> {}
