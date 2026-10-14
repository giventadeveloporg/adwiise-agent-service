package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventPoll;
import com.adwiiseagent.domain.EventPollOption;
import com.adwiiseagent.service.dto.EventPollDTO;
import com.adwiiseagent.service.dto.EventPollOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventPollOption} and its DTO {@link EventPollOptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventPollOptionMapper extends EntityMapper<EventPollOptionDTO, EventPollOption> {
    @Mapping(target = "poll", source = "poll", qualifiedByName = "eventPollId")
    EventPollOptionDTO toDto(EventPollOption s);

    @Named("eventPollId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventPollDTO toDtoEventPollId(EventPoll eventPoll);
}
