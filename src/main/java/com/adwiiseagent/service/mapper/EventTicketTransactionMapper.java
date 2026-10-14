package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventTicketTransaction;
import com.adwiiseagent.domain.EventTicketType;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventTicketTransactionDTO;
import com.adwiiseagent.service.dto.EventTicketTypeDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventTicketTransaction} and its DTO {@link EventTicketTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventTicketTransactionMapper extends EntityMapper<EventTicketTransactionDTO, EventTicketTransaction> {
    /* @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userProfileId")*/
    EventTicketTransactionDTO toDto(EventTicketTransaction s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
