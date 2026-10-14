package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.domain.TenantSettings;
import com.adwiiseagent.service.dto.TenantOrganizationDTO;
import com.adwiiseagent.service.dto.TenantSettingsDTO;
import com.adwiiseagent.service.validation.TenantSettingsHeroFieldsValidator;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TenantSettings} and its DTO {@link TenantSettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TenantSettingsMapper extends EntityMapper<TenantSettingsDTO, TenantSettings> {
    @Mapping(target = "tenantOrganization", source = "tenantOrganization", qualifiedByName = "tenantOrganizationId")
    @Mapping(target = "defaultHeroImageUrls", ignore = true)
    TenantSettingsDTO toDto(TenantSettings s);

    @Mapping(target = "addressLine1", ignore = true)
    @Mapping(target = "addressLine2", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "stateProvince", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    @Mapping(target = "country", ignore = true)
    TenantSettings toEntity(TenantSettingsDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "addressLine1", ignore = true)
    @Mapping(target = "addressLine2", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "stateProvince", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    @Mapping(target = "country", ignore = true)
    void partialUpdate(@MappingTarget TenantSettings entity, TenantSettingsDTO dto);

    @AfterMapping
    default void enrichDefaultHeroImageUrls(@MappingTarget TenantSettingsDTO dto) {
        dto.setDefaultHeroImageUrls(TenantSettingsHeroFieldsValidator.parseUrlList(dto.getDefaultHeroImageUrlsJson()));
    }

    @Named("tenantOrganizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TenantOrganizationDTO toDtoTenantOrganizationId(TenantOrganization tenantOrganization);
}
