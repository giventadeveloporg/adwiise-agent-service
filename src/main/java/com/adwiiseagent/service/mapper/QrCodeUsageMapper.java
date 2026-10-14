package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventAttendee;
import com.adwiiseagent.domain.QrCodeUsage;
import com.adwiiseagent.service.dto.EventAttendeeDTO;
import com.adwiiseagent.service.dto.QrCodeUsageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QrCodeUsage} and its DTO {@link QrCodeUsageDTO}.
 */
@Mapper(componentModel = "spring")
public interface QrCodeUsageMapper extends EntityMapper<QrCodeUsageDTO, QrCodeUsage> {
    @Mapping(target = "attendee", source = "attendee", qualifiedByName = "eventAttendeeId")
    QrCodeUsageDTO toDto(QrCodeUsage s);

    @Named("eventAttendeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventAttendeeDTO toDtoEventAttendeeId(EventAttendee eventAttendee);
}
