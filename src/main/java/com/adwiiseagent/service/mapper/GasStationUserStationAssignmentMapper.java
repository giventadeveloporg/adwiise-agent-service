package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.GasStationUserStationAssignment;
import com.adwiiseagent.service.dto.GasStationUserStationAssignmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GasStationUserStationAssignmentMapper
    extends EntityMapper<GasStationUserStationAssignmentDTO, GasStationUserStationAssignment> {}
