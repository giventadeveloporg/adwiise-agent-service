package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailUserRegionAssignment;
import com.adwiiseagent.service.dto.RetailUserRegionAssignmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailUserRegionAssignmentMapper extends EntityMapper<RetailUserRegionAssignmentDTO, RetailUserRegionAssignment> {}
