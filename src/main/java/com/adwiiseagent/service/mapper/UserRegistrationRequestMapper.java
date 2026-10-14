package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.domain.UserRegistrationRequest;
import com.adwiiseagent.service.dto.UserProfileDTO;
import com.adwiiseagent.service.dto.UserRegistrationRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserRegistrationRequest} and its DTO {@link UserRegistrationRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserRegistrationRequestMapper extends EntityMapper<UserRegistrationRequestDTO, UserRegistrationRequest> {
    @Mapping(target = "reviewedBy", source = "reviewedBy", qualifiedByName = "userProfileId")
    UserRegistrationRequestDTO toDto(UserRegistrationRequest s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
