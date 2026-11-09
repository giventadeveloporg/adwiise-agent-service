package com.adwiiseagent.service;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.DomainOwnershipCatalogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DomainOwnershipCatalogLookup {

    private final DomainOwnershipCatalogRepository repository;

    public DomainOwnershipCatalogLookup(DomainOwnershipCatalogRepository repository) {
        this.repository = repository;
    }

    public String requireAssignable(String code) {
        String normalized = code == null || code.isBlank() ? "CUSTOMER_REGISTRAR" : code.trim();
        if (repository.findActiveForTenant(normalized, null).isEmpty()) {
            throw new BadRequestAlertException(
                "Unknown domain ownership " + normalized,
                "domainOwnershipCatalog",
                "unknowndomainownership"
            );
        }
        return normalized;
    }
}
