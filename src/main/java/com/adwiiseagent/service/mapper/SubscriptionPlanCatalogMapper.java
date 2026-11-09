package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SubscriptionPlanCatalog;
import com.adwiiseagent.service.dto.SubscriptionPlanCatalogDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionPlanCatalogMapper extends EntityMapper<SubscriptionPlanCatalogDTO, SubscriptionPlanCatalog> {}
