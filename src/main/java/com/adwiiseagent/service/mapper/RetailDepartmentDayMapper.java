package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailDepartmentDay;
import com.adwiiseagent.service.dto.RetailDepartmentDayDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailDepartmentDayMapper extends EntityMapper<RetailDepartmentDayDTO, RetailDepartmentDay> {}
