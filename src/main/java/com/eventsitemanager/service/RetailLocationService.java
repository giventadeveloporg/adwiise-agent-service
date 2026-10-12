package com.eventsitemanager.service;

import com.eventsitemanager.service.dto.RetailLocationDTO;
import java.util.Optional;

public interface RetailLocationService {
    RetailLocationDTO save(RetailLocationDTO dto);
    RetailLocationDTO update(RetailLocationDTO dto);
    Optional<RetailLocationDTO> partialUpdate(RetailLocationDTO dto);
    Optional<RetailLocationDTO> findOne(Long id);
    void delete(Long id);
}
