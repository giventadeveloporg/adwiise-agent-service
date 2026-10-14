package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserProfile} and its DTO {@link UserProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends EntityMapper<UserProfileDTO, UserProfile> {
    @Mapping(target = "reviewedByAdmin", source = "reviewedByAdmin", qualifiedByName = "userProfileId")
    UserProfileDTO toDto(UserProfile s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
