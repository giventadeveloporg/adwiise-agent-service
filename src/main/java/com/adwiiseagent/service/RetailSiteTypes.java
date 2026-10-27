package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.TenantOrganizationDTO;
import java.util.Set;

/**
 * site_type RETAIL is the shop archetype. The pack code stays on retailer_type_code.
 */
public final class RetailSiteTypes {

    public static final String RETAIL = "RETAIL";
    public static final Set<String> PACKS = Set.of(
        "GAS_STATION",
        "GAS_STATION_ENT",
        "LIQUOR_STORE",
        "DUNKIN_DONUTS",
        "SEVEN_ELEVEN",
        "RESTAURANT",
        "CLINIC"
    );

    private RetailSiteTypes() {}

    public static void normalize(TenantOrganizationDTO dto) {
        if (dto == null || dto.getSiteType() == null || dto.getSiteType().isBlank()) {
            return;
        }
        String site = dto.getSiteType().trim();
        if (PACKS.contains(site)) {
            if (dto.getRetailerTypeCode() == null || dto.getRetailerTypeCode().isBlank()) {
                dto.setRetailerTypeCode(site);
            }
            dto.setSiteType(RETAIL);
        } else {
            dto.setSiteType(site);
        }
        if (dto.getRetailerTypeCode() != null) {
            String code = dto.getRetailerTypeCode().trim();
            dto.setRetailerTypeCode(code.isEmpty() ? null : code);
        }
    }
}
