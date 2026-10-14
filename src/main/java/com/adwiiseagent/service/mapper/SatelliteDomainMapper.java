package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SatelliteDomain;
import com.adwiiseagent.service.dto.SatelliteDomainDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SatelliteDomain} and its DTO {@link SatelliteDomainDTO}.
 */
@Mapper(componentModel = "spring")
public interface SatelliteDomainMapper extends EntityMapper<SatelliteDomainDTO, SatelliteDomain> {}
