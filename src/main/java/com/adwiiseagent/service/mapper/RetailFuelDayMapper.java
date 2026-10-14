package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailFuelDay;
import com.adwiiseagent.service.dto.RetailFuelDayDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailFuelDayMapper extends EntityMapper<RetailFuelDayDTO, RetailFuelDay> {}
