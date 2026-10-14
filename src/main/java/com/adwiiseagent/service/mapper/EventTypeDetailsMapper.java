package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventTypeDetails;
import com.adwiiseagent.service.dto.EventTypeDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventTypeDetails} and its DTO {@link EventTypeDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventTypeDetailsMapper extends EntityMapper<EventTypeDetailsDTO, EventTypeDetails> {}
