package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.GalleryCategory;
import com.adwiiseagent.service.dto.GalleryCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GalleryCategory} and its DTO {@link GalleryCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface GalleryCategoryMapper extends EntityMapper<GalleryCategoryDTO, GalleryCategory> {}
