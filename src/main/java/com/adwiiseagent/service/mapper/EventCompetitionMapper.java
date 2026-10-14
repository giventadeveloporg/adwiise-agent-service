package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetition;
import com.adwiiseagent.domain.EventCompetitionDay;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.service.dto.EventCompetitionDTO;
import com.adwiiseagent.service.dto.EventCompetitionDayDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetition} and its DTO {@link EventCompetitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionMapper extends EntityMapper<EventCompetitionDTO, EventCompetition> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "competitionDay", source = "competitionDay", qualifiedByName = "eventCompetitionDayId")
    EventCompetitionDTO toDto(EventCompetition s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(com.adwiiseagent.domain.EventDetails entity);

    @Named("eventCompetitionDayId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionDayDTO toDtoEventCompetitionDayId(com.adwiiseagent.domain.EventCompetitionDay entity);
}
