package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.LeagueDTO;
import java.util.Optional;

public interface LeagueService {
    LeagueDTO save(LeagueDTO dto);
    LeagueDTO update(LeagueDTO dto);
    Optional<LeagueDTO> partialUpdate(LeagueDTO dto);
    Optional<LeagueDTO> findOne(Long id);
    void delete(Long id);
}
