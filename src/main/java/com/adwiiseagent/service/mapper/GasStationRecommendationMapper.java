package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.GasStationRecommendation;
import com.adwiiseagent.service.dto.GasStationRecommendationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GasStationRecommendationMapper extends EntityMapper<GasStationRecommendationDTO, GasStationRecommendation> {}
