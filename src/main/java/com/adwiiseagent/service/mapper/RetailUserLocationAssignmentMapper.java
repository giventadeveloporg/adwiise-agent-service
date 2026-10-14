package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailUserLocationAssignment;
import com.adwiiseagent.service.dto.RetailUserLocationAssignmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailUserLocationAssignmentMapper extends EntityMapper<RetailUserLocationAssignmentDTO, RetailUserLocationAssignment> {}
