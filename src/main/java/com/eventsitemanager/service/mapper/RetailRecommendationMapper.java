package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailRecommendation;
import com.eventsitemanager.service.dto.RetailRecommendationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailRecommendationMapper extends EntityMapper<RetailRecommendationDTO, RetailRecommendation> {}
