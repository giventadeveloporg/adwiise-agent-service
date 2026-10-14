package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.LastMatch;
import com.adwiiseagent.service.dto.LastMatchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LastMatch} and its DTO {@link LastMatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface LastMatchMapper extends EntityMapper<LastMatchDTO, LastMatch> {}
