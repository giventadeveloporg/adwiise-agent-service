package com.adwiiseagent.service;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.security.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Shared catalog rows ({@code tenant_id} null) can be changed only when the admin hub has confirmed a
 * platform super admin and sent {@link #GLOBAL_WRITE_HEADER}. Tenant rows must belong to the caller tenant.
 */
@Component
public class RosterCatalogGuard {

    public static final String GLOBAL_WRITE_HEADER = "X-Catalog-Global-Write";

    public void assertWrite(boolean existingRow, String existingTenantId, String requestedTenantId) {
        String existing = normalizeTenant(existingTenantId);
        String requested = normalizeTenant(requestedTenantId);
        String caller = normalizeTenant(TenantContext.getCurrentTenant());

        if (existingRow && existing == null) {
            requireGlobalWrite();
            if (requested != null) {
                throw new BadRequestAlertException(
                    "A shared catalog row cannot be moved onto a tenant",
                    "rosterCatalog",
                    "globaltenantimmutable"
                );
            }
            return;
        }
        if (existingRow && existing != null && requested == null) {
            throw new BadRequestAlertException(
                "A tenant catalog row cannot be moved into the shared catalog",
                "rosterCatalog",
                "tenantimmutable"
            );
        }
        if (requested == null) {
            requireGlobalWrite();
            return;
        }
        if (existingRow && existing != null && !existing.equals(requested)) {
            throw new BadRequestAlertException("A catalog row cannot be moved to another tenant", "rosterCatalog", "tenantimmutable");
        }
        if (caller != null && !caller.equals(requested)) {
            throw new BadRequestAlertException("Catalog row tenant does not match the caller", "rosterCatalog", "tenantmismatch");
        }
    }

    public static String normalizeTenant(String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            return null;
        }
        return tenantId.trim();
    }

    public static String normalizeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new BadRequestAlertException("code is required", "rosterCatalog", "coderequired");
        }
        String trimmed = code.trim();
        if (trimmed.length() > 64) {
            throw new BadRequestAlertException("code must be 64 characters or fewer", "rosterCatalog", "codetoolong");
        }
        return trimmed;
    }

    private void requireGlobalWrite() {
        if (!globalWriteRequested()) {
            throw new BadRequestAlertException(
                "Only a platform super admin can change a shared catalog row",
                "rosterCatalog",
                "globalwriteforbidden"
            );
        }
    }

    public boolean isGlobalWrite() {
        return globalWriteRequested();
    }

    private boolean globalWriteRequested() {
        if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs)) {
            return false;
        }
        return "true".equalsIgnoreCase(attrs.getRequest().getHeader(GLOBAL_WRITE_HEADER));
    }
}
