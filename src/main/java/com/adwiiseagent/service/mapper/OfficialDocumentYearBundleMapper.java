package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.OfficialDocumentYearBundle;
import com.adwiiseagent.service.dto.OfficialDocumentYearBundleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link OfficialDocumentYearBundle} and {@link OfficialDocumentYearBundleDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfficialDocumentYearBundleMapper extends EntityMapper<OfficialDocumentYearBundleDTO, OfficialDocumentYearBundle> {}
