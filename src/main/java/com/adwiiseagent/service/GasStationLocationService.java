package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.GasStationLocationDTO;
import java.util.Optional;

public interface GasStationLocationService {
    GasStationLocationDTO save(GasStationLocationDTO gasStationLocationDTO);
    GasStationLocationDTO update(GasStationLocationDTO gasStationLocationDTO);
    Optional<GasStationLocationDTO> partialUpdate(GasStationLocationDTO gasStationLocationDTO);
    Optional<GasStationLocationDTO> findOne(Long id);
    void delete(Long id);
}
