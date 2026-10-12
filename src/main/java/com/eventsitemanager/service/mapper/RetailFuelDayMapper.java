package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailFuelDay;
import com.eventsitemanager.service.dto.RetailFuelDayDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailFuelDayMapper extends EntityMapper<RetailFuelDayDTO, RetailFuelDay> {}
