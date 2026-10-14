package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventGuestPricing;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventGuestPricingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventGuestPricing} and its DTO
 * {@link EventGuestPricingDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventGuestPricingMapper extends EntityMapper<EventGuestPricingDTO, EventGuestPricing> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    EventGuestPricingDTO toDto(EventGuestPricing s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);
}
