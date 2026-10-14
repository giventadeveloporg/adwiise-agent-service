package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailLocation;
import com.adwiiseagent.service.dto.RetailLocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailLocationMapper extends EntityMapper<RetailLocationDTO, RetailLocation> {}
