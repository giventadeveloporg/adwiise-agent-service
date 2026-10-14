package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventProgramDirectors;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventProgramDirectorsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventProgramDirectors} and its DTO
 * {@link EventProgramDirectorsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventProgramDirectorsMapper extends EntityMapper<EventProgramDirectorsDTO, EventProgramDirectors> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventProgramDirectorsDTO toDto(EventProgramDirectors s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);
}
