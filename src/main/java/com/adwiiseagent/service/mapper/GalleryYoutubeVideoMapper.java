package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.GalleryYoutubeVideo;
import com.adwiiseagent.service.dto.GalleryYoutubeVideoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link GalleryYoutubeVideo} and its DTO {@link GalleryYoutubeVideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface GalleryYoutubeVideoMapper extends EntityMapper<GalleryYoutubeVideoDTO, GalleryYoutubeVideo> {}
