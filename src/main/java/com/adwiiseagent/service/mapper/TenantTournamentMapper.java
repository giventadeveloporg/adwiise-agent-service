package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TenantTournament;
import com.adwiiseagent.service.dto.TenantTournamentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantTournamentMapper extends EntityMapper<TenantTournamentDTO, TenantTournament> {}
