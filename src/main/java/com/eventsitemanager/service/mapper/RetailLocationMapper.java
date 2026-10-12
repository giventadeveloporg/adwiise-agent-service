package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailLocation;
import com.eventsitemanager.service.dto.RetailLocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailLocationMapper extends EntityMapper<RetailLocationDTO, RetailLocation> {}
