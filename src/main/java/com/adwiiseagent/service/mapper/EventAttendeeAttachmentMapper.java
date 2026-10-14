package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventAttendeeAttachment;
import com.adwiiseagent.service.dto.EventAttendeeAttachmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventAttendeeAttachmentMapper extends EntityMapper<EventAttendeeAttachmentDTO, EventAttendeeAttachment> {}
