package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.FocusGroup;
import com.adwiiseagent.service.dto.FocusGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FocusGroup} and its DTO {@link FocusGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface FocusGroupMapper extends EntityMapper<FocusGroupDTO, FocusGroup> {}
