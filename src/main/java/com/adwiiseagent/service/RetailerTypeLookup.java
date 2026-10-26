package com.adwiiseagent.service;

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
            throw new BadRequestAlertException("siteType is required", "retailerType", "sitetyperequired");
        }
        if (repository.findActiveForTenant(normalized, tenantId).isEmpty()) {
            throw new BadRequestAlertException("Unknown site type " + normalized, "retailerType", "unknownsitetype");
        }
    }
}
