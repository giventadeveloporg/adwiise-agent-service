package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.CommunicationCampaign;
import com.adwiiseagent.domain.EmailLog;
import com.adwiiseagent.service.dto.CommunicationCampaignDTO;
import com.adwiiseagent.service.dto.EmailLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailLog} and its DTO {@link EmailLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmailLogMapper extends EntityMapper<EmailLogDTO, EmailLog> {
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "communicationCampaignId")
    EmailLogDTO toDto(EmailLog s);

    @Named("communicationCampaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunicationCampaignDTO toDtoCommunicationCampaignId(CommunicationCampaign communicationCampaign);
}
