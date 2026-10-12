package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailIntegration;
import com.eventsitemanager.service.dto.RetailIntegrationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailIntegrationMapper extends EntityMapper<RetailIntegrationDTO, RetailIntegration> {}
