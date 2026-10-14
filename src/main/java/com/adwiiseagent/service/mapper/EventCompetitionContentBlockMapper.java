package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetitionContentBlock;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.service.dto.EventCompetitionContentBlockDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetitionContentBlock} and its DTO {@link EventCompetitionContentBlockDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionContentBlockMapper extends EntityMapper<EventCompetitionContentBlockDTO, EventCompetitionContentBlock> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventCompetitionContentBlockDTO toDto(EventCompetitionContentBlock s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(com.adwiiseagent.domain.EventDetails entity);
}
