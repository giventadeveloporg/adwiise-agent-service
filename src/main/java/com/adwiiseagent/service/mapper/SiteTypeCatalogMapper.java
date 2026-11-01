package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SiteTypeCatalog;
import com.adwiiseagent.service.dto.SiteTypeCatalogDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteTypeCatalogMapper extends EntityMapper<SiteTypeCatalogDTO, SiteTypeCatalog> {}
