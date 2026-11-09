package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.DomainOwnershipCatalog;
import com.adwiiseagent.service.dto.DomainOwnershipCatalogDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DomainOwnershipCatalogMapper extends EntityMapper<DomainOwnershipCatalogDTO, DomainOwnershipCatalog> {}
