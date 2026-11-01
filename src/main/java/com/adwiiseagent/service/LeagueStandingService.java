package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.LeagueStandingDTO;
import java.util.Optional;

public interface LeagueStandingService {
    LeagueStandingDTO save(LeagueStandingDTO dto);
    LeagueStandingDTO update(LeagueStandingDTO dto);
    Optional<LeagueStandingDTO> partialUpdate(LeagueStandingDTO dto);
    Optional<LeagueStandingDTO> findOne(Long id);
    void delete(Long id);
}
