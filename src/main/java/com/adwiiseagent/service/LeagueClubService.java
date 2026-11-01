package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.LeagueClubDTO;
import java.util.Optional;

public interface LeagueClubService {
    LeagueClubDTO save(LeagueClubDTO dto);
    LeagueClubDTO update(LeagueClubDTO dto);
    Optional<LeagueClubDTO> partialUpdate(LeagueClubDTO dto);
    Optional<LeagueClubDTO> findOne(Long id);
    void delete(Long id);
}
