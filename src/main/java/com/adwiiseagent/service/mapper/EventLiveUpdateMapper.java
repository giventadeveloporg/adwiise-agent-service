package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventLiveUpdate;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventLiveUpdateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventLiveUpdate} and its DTO {@link EventLiveUpdateDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventLiveUpdateMapper extends EntityMapper<EventLiveUpdateDTO, EventLiveUpdate> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventLiveUpdateDTO toDto(EventLiveUpdate s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);
}
