package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.AccessRole;
import com.adwiiseagent.service.dto.AccessRoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessRoleMapper extends EntityMapper<AccessRoleDTO, AccessRole> {}
