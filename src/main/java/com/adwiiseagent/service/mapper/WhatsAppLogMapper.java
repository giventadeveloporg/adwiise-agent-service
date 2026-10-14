package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.CommunicationCampaign;
import com.adwiiseagent.domain.WhatsAppLog;
import com.adwiiseagent.service.dto.CommunicationCampaignDTO;
import com.adwiiseagent.service.dto.WhatsAppLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WhatsAppLog} and its DTO {@link WhatsAppLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface WhatsAppLogMapper extends EntityMapper<WhatsAppLogDTO, WhatsAppLog> {
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "communicationCampaignId")
    WhatsAppLogDTO toDto(WhatsAppLog s);

    @Named("communicationCampaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunicationCampaignDTO toDtoCommunicationCampaignId(CommunicationCampaign communicationCampaign);
}
