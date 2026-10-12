package com.eventsitemanager.service.impl;

import com.eventsitemanager.domain.RetailRecommendation;
import com.eventsitemanager.domain.RetailRecommendation_;
import com.eventsitemanager.domain.RetailUserLocationAssignment;
import com.eventsitemanager.domain.UserProfile;
import com.eventsitemanager.domain.enumeration.UserRoleType;
import com.eventsitemanager.repository.RetailUserLocationAssignmentRepository;
import com.eventsitemanager.repository.UserProfileRepository;
import com.eventsitemanager.security.AuthoritiesConstants;
import com.eventsitemanager.security.SecurityUtils;
import com.eventsitemanager.security.TenantContext;
import com.eventsitemanager.service.RetailAccessService;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional(readOnly = true)
public class RetailAccessServiceImpl implements RetailAccessService {

    private static final String SERVICE_JWT_SUBJECT = "jwtadmin";

    private static final Set<String> ALL_LOCATIONS_ROLES = Set.of(
        UserRoleType.SUPER_ADMIN.name(),
        UserRoleType.ADMIN.name(),
        UserRoleType.GAS_STATION_ADMIN.name()
    );

    private static final Set<String> MODULE_ROLES = Set.of(
        UserRoleType.SUPER_ADMIN.name(),
        UserRoleType.ADMIN.name(),
        UserRoleType.GAS_STATION_ADMIN.name(),
        UserRoleType.GAS_STATION_MANAGER.name()
    );

    private final UserProfileRepository userProfileRepository;
    private final RetailUserLocationAssignmentRepository assignmentRepository;

    public RetailAccessServiceImpl(
        UserProfileRepository userProfileRepository,
        RetailUserLocationAssignmentRepository assignmentRepository
    ) {
        this.userProfileRepository = userProfileRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public boolean isUnrestrictedServiceAccess() {
        if (!SecurityUtils.isAuthenticated()) {
            return false;
        }
        if (SecurityUtils.getCurrentUserLogin().filter(SERVICE_JWT_SUBJECT::equals).isPresent()) {
            return true;
        }
        return resolveCurrentUserProfile().isEmpty() && SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN);
    }

    @Override
    public void assertModuleAccess() {
        if (isUnrestrictedServiceAccess()) {
            return;
        }
        UserProfile profile = resolveCurrentUserProfile()
            .orElseThrow(() -> denied("Retail access requires an authenticated user profile."));
        if (!MODULE_ROLES.contains(normalizeRole(profile.getUserRole()))) {
            throw denied("User does not have retail module access.");
        }
    }

    @Override
    public void assertAllLocationsScope() {
        assertModuleAccess();
        if (isUnrestrictedServiceAccess()) {
            return;
        }
        UserProfile profile = resolveCurrentUserProfile().orElseThrow(() -> denied("Retail admin role required."));
        if (!ALL_LOCATIONS_ROLES.contains(normalizeRole(profile.getUserRole()))) {
            throw denied("Retail tenant admin role required for this action.");
        }
    }

    @Override
    public Set<Long> getAllowedLocationIdsOrNull() {
        if (isUnrestrictedServiceAccess()) {
            return null;
        }
        UserProfile profile = resolveCurrentUserProfile().orElse(null);
        if (profile == null) {
            return Collections.emptySet();
        }
        String role = normalizeRole(profile.getUserRole());
        if (ALL_LOCATIONS_ROLES.contains(role)) {
            return null;
        }
        if (!UserRoleType.GAS_STATION_MANAGER.name().equals(role)) {
            return Collections.emptySet();
        }
        String tenantId = TenantContext.getCurrentTenant();
        if (!StringUtils.hasText(tenantId)) {
            return Collections.emptySet();
        }
        return assignmentRepository
            .findByTenantIdAndUserProfileId(tenantId, profile.getId())
            .stream()
            .map(RetailUserLocationAssignment::getLocationId)
            .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public void assertLocationIdAllowed(Long locationId) {
        assertModuleAccess();
        if (locationId == null) {
            throw denied("Location id is required.");
        }
        Set<Long> allowed = getAllowedLocationIdsOrNull();
        if (allowed != null && !allowed.contains(locationId)) {
            throw denied("Access denied for retail location " + locationId);
        }
    }

    @Override
    public void assertRecommendationAccess(Long locationId) {
        assertModuleAccess();
        if (isUnrestrictedServiceAccess()) {
            return;
        }
        Set<Long> allowed = getAllowedLocationIdsOrNull();
        if (allowed == null) {
            return;
        }
        if (locationId == null) {
            if (allowed.isEmpty()) {
                throw denied("Access denied for a chain-level retail recommendation.");
            }
            return;
        }
        if (!allowed.contains(locationId)) {
            throw denied("Access denied for retail recommendation at location " + locationId);
        }
    }

    @Override
    public void restrict(LongFilter filter, Set<Long> allowed) {
        if (allowed.isEmpty()) {
            filter.setIn(List.of(-1L));
            return;
        }
        if (filter.getEquals() != null) {
            if (!allowed.contains(filter.getEquals())) {
                throw denied("Access denied for the requested location.");
            }
            return;
        }
        filter.setIn(allowed.stream().sorted().toList());
    }

    @Override
    public Specification<RetailRecommendation> recommendationSpecification() {
        if (isUnrestrictedServiceAccess()) {
            return Specification.where(null);
        }
        Set<Long> allowed = getAllowedLocationIdsOrNull();
        if (allowed == null) {
            return Specification.where(null);
        }
        if (allowed.isEmpty()) {
            return (root, query, cb) -> cb.disjunction();
        }
        return (root, query, cb) ->
            cb.or(cb.isNull(root.get(RetailRecommendation_.locationId)), root.get(RetailRecommendation_.locationId).in(allowed));
    }

    private Optional<UserProfile> resolveCurrentUserProfile() {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isEmpty()) {
            return Optional.empty();
        }
        String tenantId = TenantContext.getCurrentTenant();
        if (!StringUtils.hasText(tenantId)) {
            return Optional.empty();
        }
        return userProfileRepository.findByUserIdAndTenantId(login.orElseThrow(), tenantId);
    }

    private static String normalizeRole(String role) {
        return role == null ? "" : role.trim().toUpperCase();
    }

    private static AccessDeniedException denied(String message) {
        return new AccessDeniedException(message);
    }
}
