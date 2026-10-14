package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetitionSettings;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.service.dto.EventCompetitionSettingsDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetitionSettings} and its DTO {@link EventCompetitionSettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionSettingsMapper extends EntityMapper<EventCompetitionSettingsDTO, EventCompetitionSettings> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventCompetitionSettingsDTO toDto(EventCompetitionSettings s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(com.adwiiseagent.domain.EventDetails entity);
}
