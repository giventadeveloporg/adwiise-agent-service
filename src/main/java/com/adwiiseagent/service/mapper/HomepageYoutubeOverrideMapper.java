package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.HomepageYoutubeOverride;
import com.adwiiseagent.service.dto.HomepageYoutubeOverrideDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link HomepageYoutubeOverride} and its DTO {@link HomepageYoutubeOverrideDTO}.
 */
@Mapper(componentModel = "spring")
public interface HomepageYoutubeOverrideMapper extends EntityMapper<HomepageYoutubeOverrideDTO, HomepageYoutubeOverride> {}
