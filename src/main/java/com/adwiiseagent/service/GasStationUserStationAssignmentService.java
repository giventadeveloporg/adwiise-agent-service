package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.GasStationUserStationAssignmentDTO;
import java.util.Optional;

public interface GasStationUserStationAssignmentService {
    GasStationUserStationAssignmentDTO save(GasStationUserStationAssignmentDTO gasStationUserStationAssignmentDTO);
    GasStationUserStationAssignmentDTO update(GasStationUserStationAssignmentDTO gasStationUserStationAssignmentDTO);
    Optional<GasStationUserStationAssignmentDTO> partialUpdate(GasStationUserStationAssignmentDTO gasStationUserStationAssignmentDTO);
    Optional<GasStationUserStationAssignmentDTO> findOne(Long id);
    void delete(Long id);
}
