package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolFacilityDTO;
import java.util.Optional;

public interface SchoolFacilityService {
    SchoolFacilityDTO save(SchoolFacilityDTO schoolFacilityDTO);

    SchoolFacilityDTO update(SchoolFacilityDTO schoolFacilityDTO);

    Optional<SchoolFacilityDTO> partialUpdate(SchoolFacilityDTO schoolFacilityDTO);

    Optional<SchoolFacilityDTO> findOne(Long id);

    void delete(Long id);
}
