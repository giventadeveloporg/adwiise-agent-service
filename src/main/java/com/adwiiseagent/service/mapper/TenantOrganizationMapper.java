package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.service.dto.TenantOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TenantOrganization} and its DTO {@link TenantOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface TenantOrganizationMapper extends EntityMapper<TenantOrganizationDTO, TenantOrganization> {}
