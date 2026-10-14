package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.RetailUserLocationAssignmentDTO;
import java.util.Optional;

public interface RetailUserLocationAssignmentService {
    RetailUserLocationAssignmentDTO save(RetailUserLocationAssignmentDTO dto);
    RetailUserLocationAssignmentDTO update(RetailUserLocationAssignmentDTO dto);
    Optional<RetailUserLocationAssignmentDTO> partialUpdate(RetailUserLocationAssignmentDTO dto);
    Optional<RetailUserLocationAssignmentDTO> findOne(Long id);
    void delete(Long id);
}
