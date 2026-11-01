package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.LeagueStanding;
import com.adwiiseagent.service.dto.LeagueStandingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeagueStandingMapper extends EntityMapper<LeagueStandingDTO, LeagueStanding> {}
