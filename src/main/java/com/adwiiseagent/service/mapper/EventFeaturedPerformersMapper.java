package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventFeaturedPerformers;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventFeaturedPerformersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventFeaturedPerformers} and its DTO
 * {@link EventFeaturedPerformersDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventFeaturedPerformersMapper extends EntityMapper<EventFeaturedPerformersDTO, EventFeaturedPerformers> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventFeaturedPerformersDTO toDto(EventFeaturedPerformers s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);
}
