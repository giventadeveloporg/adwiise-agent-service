package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.domain.UserTask;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import com.adwiiseagent.service.dto.UserTaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserTask} and its DTO {@link UserTaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserTaskMapper extends EntityMapper<UserTaskDTO, UserTask> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userProfileId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    UserTaskDTO toDto(UserTask s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);
}
