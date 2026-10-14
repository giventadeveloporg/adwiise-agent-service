package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailIntegration;
import com.adwiiseagent.service.dto.RetailIntegrationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailIntegrationMapper extends EntityMapper<RetailIntegrationDTO, RetailIntegration> {}
