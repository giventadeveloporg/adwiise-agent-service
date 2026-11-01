package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailerType;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailerTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RetailerTypeLookup {

    private final RetailerTypeRepository repository;

    public RetailerTypeLookup(RetailerTypeRepository repository) {
        this.repository = repository;
    }

    public void requireAssignable(String code, String tenantId) {
        String normalized = code == null ? "" : code.trim();
        if (normalized.isEmpty()) {
            throw new BadRequestAlertException("retailerType is required", "retailerType", "packrequired");
        }
        if (repository.findActiveForTenant(normalized, tenantId).isEmpty()) {
            throw new BadRequestAlertException("Unknown retailer type " + normalized, "retailerType", "unknownretailertype");
        }
    }

    public void requirePackForSite(String code, String tenantId, String siteTypeCode) {
        String normalized = code == null ? "" : code.trim();
        if (normalized.isEmpty()) {
            throw new BadRequestAlertException("retailerType is required", "retailerType", "packrequired");
        }
        RetailerType pack = repository
            .findActiveForTenant(normalized, tenantId)
            .stream()
            .findFirst()
            .orElseThrow(() -> new BadRequestAlertException("Unknown retailer type " + normalized, "retailerType", "unknownretailertype"));
        String parent = pack.getSiteTypeCode() == null ? "" : pack.getSiteTypeCode();
        if (!siteTypeCode.equals(parent)) {
            throw new BadRequestAlertException("This pack does not belong to " + siteTypeCode, "retailerType", "packsite");
        }
    }
}
