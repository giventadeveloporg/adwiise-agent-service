package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailerType;
import com.adwiiseagent.service.dto.RetailerTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailerTypeMapper extends EntityMapper<RetailerTypeDTO, RetailerType> {}
