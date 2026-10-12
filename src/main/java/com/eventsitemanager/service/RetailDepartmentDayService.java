package com.eventsitemanager.service;

import com.eventsitemanager.service.dto.RetailDepartmentDayDTO;
import java.util.Optional;

public interface RetailDepartmentDayService {
    RetailDepartmentDayDTO save(RetailDepartmentDayDTO dto);
    RetailDepartmentDayDTO update(RetailDepartmentDayDTO dto);
    Optional<RetailDepartmentDayDTO> partialUpdate(RetailDepartmentDayDTO dto);
    Optional<RetailDepartmentDayDTO> findOne(Long id);
    void delete(Long id);
}
