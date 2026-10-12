package com.eventsitemanager.service.mapper;

import com.eventsitemanager.domain.RetailDepartmentDay;
import com.eventsitemanager.service.dto.RetailDepartmentDayDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailDepartmentDayMapper extends EntityMapper<RetailDepartmentDayDTO, RetailDepartmentDay> {}
