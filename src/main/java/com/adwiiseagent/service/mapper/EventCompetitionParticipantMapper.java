package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventCompetitionParticipant;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.EventCompetitionParticipantDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCompetitionParticipant} and its DTO {@link EventCompetitionParticipantDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventCompetitionParticipantMapper extends EntityMapper<EventCompetitionParticipantDTO, EventCompetitionParticipant> {
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userProfileId")
    @Mapping(target = "guardianUserProfile", source = "guardianUserProfile", qualifiedByName = "guardianUserProfileId")
    EventCompetitionParticipantDTO toDto(EventCompetitionParticipant s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(com.adwiiseagent.domain.UserProfile entity);

    @Named("guardianUserProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoGuardianUserProfileId(com.adwiiseagent.domain.UserProfile entity);
}
