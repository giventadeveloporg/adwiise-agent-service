package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailUserLocationAssignment;
import com.eventsitemanager.service.dto.RetailUserLocationAssignmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailUserLocationAssignmentMapper extends EntityMapper<RetailUserLocationAssignmentDTO, RetailUserLocationAssignment> {}
