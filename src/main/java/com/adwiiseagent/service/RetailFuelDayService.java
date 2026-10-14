package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.RetailFuelDayDTO;
import java.util.Optional;

public interface RetailFuelDayService {
    RetailFuelDayDTO save(RetailFuelDayDTO dto);
    RetailFuelDayDTO update(RetailFuelDayDTO dto);
    Optional<RetailFuelDayDTO> partialUpdate(RetailFuelDayDTO dto);
    Optional<RetailFuelDayDTO> findOne(Long id);
    void delete(Long id);
}
