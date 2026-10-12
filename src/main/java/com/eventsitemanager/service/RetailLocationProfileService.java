package com.eventsitemanager.service;

import com.eventsitemanager.service.dto.RetailLocationProfileDTO;
import java.util.Optional;

public interface RetailLocationProfileService {
    RetailLocationProfileDTO save(RetailLocationProfileDTO dto);
    RetailLocationProfileDTO update(RetailLocationProfileDTO dto);
    Optional<RetailLocationProfileDTO> partialUpdate(RetailLocationProfileDTO dto);
    Optional<RetailLocationProfileDTO> findOne(Long id);
    void delete(Long id);
}
