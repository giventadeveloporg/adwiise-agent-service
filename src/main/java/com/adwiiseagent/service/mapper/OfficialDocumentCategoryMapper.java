package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.OfficialDocumentCategory;
import com.adwiiseagent.service.dto.OfficialDocumentCategoryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link OfficialDocumentCategory} and {@link OfficialDocumentCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfficialDocumentCategoryMapper extends EntityMapper<OfficialDocumentCategoryDTO, OfficialDocumentCategory> {}
