package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventScoreCard;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventScoreCardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventScoreCard} and its DTO {@link EventScoreCardDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventScoreCardMapper extends EntityMapper<EventScoreCardDTO, EventScoreCard> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventScoreCardDTO toDto(EventScoreCard s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);
}
