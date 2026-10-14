package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventSponsors;
import com.adwiiseagent.service.dto.EventSponsorsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventSponsors} and its DTO
 * {@link EventSponsorsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventSponsorsMapper extends EntityMapper<EventSponsorsDTO, EventSponsors> {
    EventSponsorsDTO toDto(EventSponsors s);
}
