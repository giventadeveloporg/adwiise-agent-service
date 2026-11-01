package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.LeagueClub;
import com.adwiiseagent.service.dto.LeagueClubDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeagueClubMapper extends EntityMapper<LeagueClubDTO, LeagueClub> {}
