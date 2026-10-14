package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.RetailLocationProfileDTO;
import java.util.Optional;

public interface RetailLocationProfileService {
    RetailLocationProfileDTO save(RetailLocationProfileDTO dto);
    RetailLocationProfileDTO update(RetailLocationProfileDTO dto);
    Optional<RetailLocationProfileDTO> partialUpdate(RetailLocationProfileDTO dto);
    Optional<RetailLocationProfileDTO> findOne(Long id);
    void delete(Long id);
}
