package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetitionDay;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.service.dto.EventCompetitionDayDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetitionDay} and its DTO {@link EventCompetitionDayDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionDayMapper extends EntityMapper<EventCompetitionDayDTO, EventCompetitionDay> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventCompetitionDayDTO toDto(EventCompetitionDay s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(com.adwiiseagent.domain.EventDetails entity);
}
