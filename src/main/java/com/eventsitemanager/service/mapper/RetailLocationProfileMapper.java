package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailLocationProfile;
import com.eventsitemanager.service.dto.RetailLocationProfileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailLocationProfileMapper extends EntityMapper<RetailLocationProfileDTO, RetailLocationProfile> {}
