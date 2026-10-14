package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCalendarEntry;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.EventCalendarEntryDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCalendarEntry} and its DTO {@link EventCalendarEntryDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCalendarEntryMapper extends EntityMapper<EventCalendarEntryDTO, EventCalendarEntry> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userProfileId")
    EventCalendarEntryDTO toDto(EventCalendarEntry s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
