package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolDepartmentDTO;
import java.util.Optional;

public interface SchoolDepartmentService {
    SchoolDepartmentDTO save(SchoolDepartmentDTO schoolDepartmentDTO);

    SchoolDepartmentDTO update(SchoolDepartmentDTO schoolDepartmentDTO);

    Optional<SchoolDepartmentDTO> partialUpdate(SchoolDepartmentDTO schoolDepartmentDTO);

    Optional<SchoolDepartmentDTO> findOne(Long id);

    void delete(Long id);
}
