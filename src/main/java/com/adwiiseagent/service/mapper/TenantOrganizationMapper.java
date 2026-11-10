package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.service.dto.TenantOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TenantOrganization} and its DTO {@link TenantOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface TenantOrganizationMapper extends EntityMapper<TenantOrganizationDTO, TenantOrganization> {
    @Override
    @Mapping(target = "parentOrganization", ignore = true)
    TenantOrganization toEntity(TenantOrganizationDTO dto);

    @Override
    @Mapping(target = "parentOrganizationId", source = "parentOrganization.id")
    TenantOrganizationDTO toDto(TenantOrganization entity);

    @Override
    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "parentOrganization", ignore = true)
    void partialUpdate(@MappingTarget TenantOrganization entity, TenantOrganizationDTO dto);
}
