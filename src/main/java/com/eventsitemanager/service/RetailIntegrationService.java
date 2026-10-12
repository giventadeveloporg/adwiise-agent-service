package com.eventsitemanager.service;

import com.eventsitemanager.service.dto.RetailIntegrationDTO;
import java.util.Optional;

public interface RetailIntegrationService {
    RetailIntegrationDTO save(RetailIntegrationDTO dto);
    RetailIntegrationDTO update(RetailIntegrationDTO dto);
    Optional<RetailIntegrationDTO> partialUpdate(RetailIntegrationDTO dto);
    Optional<RetailIntegrationDTO> findOne(Long id);
    void delete(Long id);
}
