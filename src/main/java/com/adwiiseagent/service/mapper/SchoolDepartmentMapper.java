package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolDepartment;
import com.adwiiseagent.service.dto.SchoolDepartmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolDepartmentMapper extends EntityMapper<SchoolDepartmentDTO, SchoolDepartment> {}
