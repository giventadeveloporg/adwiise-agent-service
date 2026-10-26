package com.adwiiseagent.service;

import com.adwiiseagent.domain.AccessRole;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.AccessRoleRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccessRoleLookup {

    public enum Scope {
        ALL,
        ASSIGNED,
        NONE,
    }

    private static final Set<String> FALLBACK_ALL = Set.of("SUPER_ADMIN", "ADMIN", "GAS_STATION_ADMIN");

    private final AccessRoleRepository repository;

    public AccessRoleLookup(AccessRoleRepository repository) {
        this.repository = repository;
    }

    public void requireAssignable(String code, String tenantId) {
        String normalized = code == null ? "" : code.trim();
        if (normalized.isEmpty()) {
            return;
        }
        if (findActive(normalized, tenantId).isEmpty()) {
            throw new BadRequestAlertException("Unknown access role " + normalized, "accessRole", "unknownrole");
        }
    }

    public boolean grantsAdmin(String code, String tenantId) {
        if (code == null || code.isBlank()) {
            return false;
        }
        String normalized = code.trim();
        if ("ADMIN".equals(normalized) || "SUPER_ADMIN".equals(normalized)) {
            return true;
        }
        return findActive(normalized, tenantId).stream().anyMatch(row -> Boolean.TRUE.equals(row.getGrantsAdmin()));
    }

    public Scope locationScope(String code, String tenantId) {
        if (code == null || code.isBlank()) {
            return Scope.NONE;
        }
        String normalized = code.trim();
        List<AccessRole> rows = findActive(normalized, tenantId);
        if (!rows.isEmpty() && rows.get(0).getLocationScope() != null) {
            return parse(rows.get(0).getLocationScope());
        }
        if (FALLBACK_ALL.contains(normalized)) {
            return Scope.ALL;
        }
        if ("GAS_STATION_MANAGER".equals(normalized)) {
            return Scope.ASSIGNED;
        }
        return Scope.NONE;
    }

    private List<AccessRole> findActive(String code, String tenantId) {
        return repository.findActiveForTenant(code, tenantId);
    }

    private static Scope parse(String raw) {
        if ("ALL".equalsIgnoreCase(raw)) {
            return Scope.ALL;
        }
        if ("ASSIGNED".equalsIgnoreCase(raw)) {
            return Scope.ASSIGNED;
        }
        return Scope.NONE;
    }
}
