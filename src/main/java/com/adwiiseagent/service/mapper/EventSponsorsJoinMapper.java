package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventSponsors;
import com.adwiiseagent.domain.EventSponsorsJoin;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventSponsorsDTO;
import com.adwiiseagent.service.dto.EventSponsorsJoinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventSponsorsJoin} and its DTO
 * {@link EventSponsorsJoinDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventSponsorsJoinMapper extends EntityMapper<EventSponsorsJoinDTO, EventSponsorsJoin> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "sponsor", source = "sponsor", qualifiedByName = "eventSponsorsId")
    EventSponsorsJoinDTO toDto(EventSponsorsJoin s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);

    @Named("eventSponsorsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventSponsorsDTO toDtoEventSponsorsId(EventSponsors eventSponsors);
}
