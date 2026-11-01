package com.adwiiseagent.service;

import com.adwiiseagent.domain.SiteTypeCatalog;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SiteTypeCatalogRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SiteTypeCatalogLookup {

    private final SiteTypeCatalogRepository repository;

    public SiteTypeCatalogLookup(SiteTypeCatalogRepository repository) {
        this.repository = repository;
    }

    public void requireAssignable(String code, String tenantId) {
        if (findActive(code, tenantId).isEmpty()) {
            String normalized = code == null ? "" : code.trim();
            if (normalized.isEmpty()) {
                throw new BadRequestAlertException("siteType is required", "siteTypeCatalog", "sitetyperequired");
            }
            throw new BadRequestAlertException("Unknown site type " + normalized, "siteTypeCatalog", "unknownsitetype");
        }
    }

    public List<SiteTypeCatalog> findActive(String code, String tenantId) {
        String normalized = code == null ? "" : code.trim();
        if (normalized.isEmpty()) {
            return List.of();
        }
        return repository.findActiveForTenant(normalized, tenantId);
    }
}
