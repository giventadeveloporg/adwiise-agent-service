package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.LeagueMatch;
import com.adwiiseagent.service.dto.LeagueMatchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeagueMatchMapper extends EntityMapper<LeagueMatchDTO, LeagueMatch> {}
