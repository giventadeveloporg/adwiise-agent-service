package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetition;
import com.adwiiseagent.domain.EventCompetitionParticipant;
import com.adwiiseagent.domain.EventCompetitionRegistration;
import com.adwiiseagent.domain.EventCompetitionRegistration;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.EventCompetitionDTO;
import com.adwiiseagent.service.dto.EventCompetitionParticipantDTO;
import com.adwiiseagent.service.dto.EventCompetitionRegistrationDTO;
import com.adwiiseagent.service.dto.EventCompetitionRegistrationDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetitionRegistration} and its DTO {@link EventCompetitionRegistrationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionRegistrationMapper extends EntityMapper<EventCompetitionRegistrationDTO, EventCompetitionRegistration> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "competition", source = "competition", qualifiedByName = "eventCompetitionId")
    @Mapping(target = "participantProfile", source = "participantProfile", qualifiedByName = "eventCompetitionParticipantId")
    @Mapping(target = "groupLeaderRegistration", source = "groupLeaderRegistration", qualifiedByName = "eventCompetitionRegistrationId")
    @Mapping(target = "registeredByUserProfile", source = "registeredByUserProfile", qualifiedByName = "registeredByUserProfileId")
    EventCompetitionRegistrationDTO toDto(EventCompetitionRegistration s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(com.adwiiseagent.domain.EventDetails entity);

    @Named("eventCompetitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionDTO toDtoEventCompetitionId(com.adwiiseagent.domain.EventCompetition entity);

    @Named("eventCompetitionParticipantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionParticipantDTO toDtoEventCompetitionParticipantId(com.adwiiseagent.domain.EventCompetitionParticipant entity);

    @Named("eventCompetitionRegistrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionRegistrationDTO toDtoEventCompetitionRegistrationId(com.adwiiseagent.domain.EventCompetitionRegistration entity);

    @Named("registeredByUserProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoRegisteredByUserProfileId(com.adwiiseagent.domain.UserProfile entity);
}
