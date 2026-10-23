package com.adwiiseagent.service;

import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.domain.enumeration.UserRoleType;
import com.adwiiseagent.domain.enumeration.UserStatusType;
import com.adwiiseagent.repository.UserProfileRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Resolves a Clerk sign-in to a user_profile row.
 * A person is (user_id, tenant_id, email). user_id is valid only inside the Clerk application that issued it.
 * When {@code clerk.identity.separate-pool} is true (dev), a miss inserts a new row and leaves any sibling row unchanged.
 * When it is false (production), a miss patches user_id on the single email row for that tenant.
 */
@Service
@Transactional
public class ClerkProfileIdentityService {

    private static final Logger log = LoggerFactory.getLogger(ClerkProfileIdentityService.class);

    private final UserProfileRepository userProfileRepository;

    @Value("${clerk.identity.separate-pool:false}")
    private boolean separatePool;

    public ClerkProfileIdentityService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public boolean isSeparatePool() {
        return separatePool;
    }

    public ClerkProfileResolution resolve(
        String clerkUserId,
        String tenantId,
        String email,
        String firstName,
        String lastName,
        String profileImageUrl,
        String authProvider
    ) {
        if (clerkUserId == null || clerkUserId.isBlank() || tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("clerkUserId and tenantId are required");
        }

        String normalizedEmail = UserProfile.normalizeEmail(email);

        Optional<UserProfile> byUserId = userProfileRepository.findByUserIdAndTenantId(clerkUserId, tenantId);
        if (byUserId.isPresent()) {
            return new ClerkProfileResolution(touchOwned(byUserId.orElseThrow(), normalizedEmail, firstName, lastName, profileImageUrl), false);
        }

        Optional<UserProfile> byClerkUserId = userProfileRepository.findByClerkUserIdAndTenantId(clerkUserId, tenantId);
        if (byClerkUserId.isPresent()) {
            UserProfile row = byClerkUserId.orElseThrow();
            if (row.getUserId() != null && !clerkUserId.equals(row.getUserId())) {
                log.warn(
                    "Clerk user {} matches clerk_user_id on profile {} which already has user_id {}. Leaving user_id unchanged.",
                    clerkUserId,
                    row.getId(),
                    row.getUserId()
                );
            }
            return new ClerkProfileResolution(touchOwned(row, normalizedEmail, firstName, lastName, profileImageUrl), false);
        }

        List<UserProfile> siblings = normalizedEmail == null
            ? List.of()
            : userProfileRepository.findAllByEmailAndTenantIdOrderByIdAsc(normalizedEmail, tenantId);

        if (separatePool) {
            UserProfile created = newRow(clerkUserId, tenantId, normalizedEmail, firstName, lastName, profileImageUrl, authProvider);
            if (!siblings.isEmpty()) {
                UserProfile sibling = siblings.get(0);
                created.setFirstName(sibling.getFirstName());
                created.setLastName(sibling.getLastName());
                created.setUserRole(sibling.getUserRole());
                created.setUserStatus(sibling.getUserStatus());
                log.info(
                    "Dev Clerk pool inserted profile for user {} tenant {} by copying sibling {}",
                    clerkUserId,
                    tenantId,
                    sibling.getId()
                );
            } else {
                created.setUserRole(UserRoleType.MEMBER.name());
                created.setUserStatus(UserStatusType.PENDING_APPROVAL.name());
                log.info("Dev Clerk pool inserted new member profile for user {} tenant {}", clerkUserId, tenantId);
            }
            return new ClerkProfileResolution(userProfileRepository.save(created), true);
        }

        if (!siblings.isEmpty()) {
            UserProfile row = siblings.get(0);
            if (!clerkUserId.equals(row.getUserId())) {
                log.info("Production Clerk pool updated user_id on profile {} for tenant {}", row.getId(), tenantId);
                row.setUserId(clerkUserId);
            }
            row.setClerkUserId(clerkUserId);
            return new ClerkProfileResolution(touchOwned(row, normalizedEmail, firstName, lastName, profileImageUrl), false);
        }

        UserProfile created = newRow(clerkUserId, tenantId, normalizedEmail, firstName, lastName, profileImageUrl, authProvider);
        created.setUserRole(UserRoleType.MEMBER.name());
        created.setUserStatus(UserStatusType.PENDING_APPROVAL.name());
        return new ClerkProfileResolution(userProfileRepository.save(created), true);
    }

    private UserProfile touchOwned(
        UserProfile row,
        String normalizedEmail,
        String firstName,
        String lastName,
        String profileImageUrl
    ) {
        ZonedDateTime now = ZonedDateTime.now();
        row.setLastSignInAt(now);
        row.setUpdatedAt(now);
        if (normalizedEmail != null && !normalizedEmail.equals(row.getEmail())) {
            row.setEmail(normalizedEmail);
        }
        if (firstName != null && !firstName.isBlank() && !firstName.equals(row.getFirstName())) {
            row.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isBlank() && !lastName.equals(row.getLastName())) {
            row.setLastName(lastName);
        }
        if (profileImageUrl != null && !profileImageUrl.isBlank()) {
            row.setProfileImageUrl(profileImageUrl);
        }
        return userProfileRepository.save(row);
    }

    private UserProfile newRow(
        String clerkUserId,
        String tenantId,
        String normalizedEmail,
        String firstName,
        String lastName,
        String profileImageUrl,
        String authProvider
    ) {
        ZonedDateTime now = ZonedDateTime.now();
        UserProfile created = new UserProfile();
        created.setUserId(clerkUserId);
        created.setClerkUserId(clerkUserId);
        created.setEmail(normalizedEmail);
        created.setTenantId(tenantId);
        created.setFirstName(firstName);
        created.setLastName(lastName);
        created.setProfileImageUrl(profileImageUrl);
        created.setAuthProvider(authProvider == null || authProvider.isBlank() ? "clerk" : authProvider);
        created.setEmailVerified(true);
        created.setIsEmailSubscribed(normalizedEmail != null);
        created.setCreatedAt(now);
        created.setUpdatedAt(now);
        created.setLastSignInAt(now);
        return created;
    }

    public record ClerkProfileResolution(UserProfile profile, boolean created) {}
}
