package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventAgendaItem;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventMedia;
import com.adwiiseagent.service.dto.EventAgendaItemDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventMediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventAgendaItem} and its DTO {@link EventAgendaItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventAgendaItemMapper extends EntityMapper<EventAgendaItemDTO, EventAgendaItem> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "eventMedia", source = "eventMedia", qualifiedByName = "eventMediaId")
    EventAgendaItemDTO toDto(EventAgendaItem s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails entity);

    @Named("eventMediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileUrl", source = "fileUrl")
    @Mapping(target = "preSignedUrl", source = "preSignedUrl")
    EventMediaDTO toDtoEventMediaId(EventMedia entity);
}
