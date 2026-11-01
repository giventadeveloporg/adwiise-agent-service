package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.LeagueMatchDTO;
import java.util.Optional;

public interface LeagueMatchService {
    LeagueMatchDTO save(LeagueMatchDTO dto);
    LeagueMatchDTO update(LeagueMatchDTO dto);
    Optional<LeagueMatchDTO> partialUpdate(LeagueMatchDTO dto);
    Optional<LeagueMatchDTO> findOne(Long id);
    void delete(Long id);
}
