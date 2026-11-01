package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.League;
import com.adwiiseagent.service.dto.LeagueDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeagueMapper extends EntityMapper<LeagueDTO, League> {}
