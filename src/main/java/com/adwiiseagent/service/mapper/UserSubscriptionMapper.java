package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.domain.UserSubscription;
import com.adwiiseagent.service.dto.UserProfileDTO;
import com.adwiiseagent.service.dto.UserSubscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSubscription} and its DTO {@link UserSubscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserSubscriptionMapper extends EntityMapper<UserSubscriptionDTO, UserSubscription> {
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userProfileId")
    UserSubscriptionDTO toDto(UserSubscription s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
