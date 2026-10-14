package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventAdmin;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.EventAdminDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventAdmin} and its DTO {@link EventAdminDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventAdminMapper extends EntityMapper<EventAdminDTO, EventAdmin> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userProfileId")
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userProfileId")
    EventAdminDTO toDto(EventAdmin s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
