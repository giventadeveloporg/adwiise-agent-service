package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetition;
import com.adwiiseagent.domain.EventCompetitionParticipant;
import com.adwiiseagent.domain.EventCompetitionRegistration;
import com.adwiiseagent.domain.EventCompetitionResult;
import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventMedia;
import com.adwiiseagent.service.dto.EventCompetitionDTO;
import com.adwiiseagent.service.dto.EventCompetitionParticipantDTO;
import com.adwiiseagent.service.dto.EventCompetitionRegistrationDTO;
import com.adwiiseagent.service.dto.EventCompetitionResultDTO;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventMediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetitionResult} and its DTO {@link EventCompetitionResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionResultMapper extends EntityMapper<EventCompetitionResultDTO, EventCompetitionResult> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "competition", source = "competition", qualifiedByName = "eventCompetitionId")
    @Mapping(target = "participantProfile", source = "participantProfile", qualifiedByName = "eventCompetitionParticipantId")
    @Mapping(target = "registration", source = "registration", qualifiedByName = "eventCompetitionRegistrationId")
    @Mapping(target = "winnerMedia", source = "winnerMedia", qualifiedByName = "eventMediaId")
    @Mapping(target = "workMedia", source = "workMedia", qualifiedByName = "eventMediaId")
    EventCompetitionResultDTO toDto(EventCompetitionResult s);

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(com.adwiiseagent.domain.EventDetails entity);

    @Named("eventCompetitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EventCompetitionDTO toDtoEventCompetitionId(com.adwiiseagent.domain.EventCompetition entity);

    @Named("eventCompetitionParticipantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionParticipantDTO toDtoEventCompetitionParticipantId(com.adwiiseagent.domain.EventCompetitionParticipant entity);

    @Named("eventCompetitionRegistrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionRegistrationDTO toDtoEventCompetitionRegistrationId(com.adwiiseagent.domain.EventCompetitionRegistration entity);

    @Named("eventMediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventMediaDTO toDtoEventMediaId(com.adwiiseagent.domain.EventMedia entity);
}
