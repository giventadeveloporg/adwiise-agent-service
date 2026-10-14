package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetitionGroupMember;
import com.adwiiseagent.domain.EventCompetitionParticipant;
import com.adwiiseagent.domain.EventCompetitionRegistration;
import com.adwiiseagent.service.dto.EventCompetitionGroupMemberDTO;
import com.adwiiseagent.service.dto.EventCompetitionParticipantDTO;
import com.adwiiseagent.service.dto.EventCompetitionRegistrationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetitionGroupMember} and its DTO {@link EventCompetitionGroupMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionGroupMemberMapper extends EntityMapper<EventCompetitionGroupMemberDTO, EventCompetitionGroupMember> {
    @Mapping(target = "registration", source = "registration", qualifiedByName = "eventCompetitionRegistrationId")
    @Mapping(target = "participantProfile", source = "participantProfile", qualifiedByName = "eventCompetitionParticipantId")
    EventCompetitionGroupMemberDTO toDto(EventCompetitionGroupMember s);

    @Named("eventCompetitionRegistrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionRegistrationDTO toDtoEventCompetitionRegistrationId(EventCompetitionRegistration entity);

    @Named("eventCompetitionParticipantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventCompetitionParticipantDTO toDtoEventCompetitionParticipantId(EventCompetitionParticipant entity);
}
