package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ProfileMediaAsset;
import com.adwiiseagent.service.dto.ProfileMediaAssetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMediaAssetMapper extends EntityMapper<ProfileMediaAssetDTO, ProfileMediaAsset> {}
