package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.RetailUserRegionAssignmentDTO;
import java.util.Optional;

public interface RetailUserRegionAssignmentService {
    RetailUserRegionAssignmentDTO save(RetailUserRegionAssignmentDTO dto);
    RetailUserRegionAssignmentDTO update(RetailUserRegionAssignmentDTO dto);
    Optional<RetailUserRegionAssignmentDTO> partialUpdate(RetailUserRegionAssignmentDTO dto);
    Optional<RetailUserRegionAssignmentDTO> findOne(Long id);
    void delete(Long id);
}
