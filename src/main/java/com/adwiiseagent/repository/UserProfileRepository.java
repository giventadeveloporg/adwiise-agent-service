package com.adwiiseagent.repository;

import com.adwiiseagent.domain.UserProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
    /**
     * All tenant-scoped profiles for a Clerk/backend user id. Prefer this over a single-result query when
     * {@code user_id} is not globally unique.
     */
    List<UserProfile> findAllByUserId(String userId);

    boolean existsByUserId(String userId);

    // Multi-tenant: at most one profile per (user_id, tenant_id)
    Optional<UserProfile> findByUserIdAndTenantId(String userId, String tenantId);

    /**
     * Email is not unique. Callers that need one row must order by id and pick explicitly.
     * Sign-in must use {@code findByUserIdAndTenantId} or {@code findByClerkUserIdAndTenantId} first.
     */
    List<UserProfile> findAllByEmailOrderByIdAsc(String email);

    List<UserProfile> findAllByEmailAndTenantIdOrderByIdAsc(String email, String tenantId);

    List<UserProfile> findByTenantIdAndUserRoleIn(String tenantId, java.util.Collection<String> userRoles);

    // New: Find all subscribed emails for a tenant
    @Query(
        "SELECT DISTINCT u.email FROM UserProfile u WHERE u.tenantId = :tenantId AND u.isEmailSubscribed = true AND u.email IS NOT NULL"
    )
    List<String> findSubscribedEmailsByTenantId(@Param("tenantId") String tenantId);

    // New: Find subscribed users with pagination (includes all user data needed for
    // email sending)
    @Query(
        "SELECT u FROM UserProfile u WHERE u.tenantId = :tenantId AND u.isEmailSubscribed = true AND u.email IS NOT NULL AND u.emailSubscriptionToken IS NOT NULL"
    )
    Page<UserProfile> findSubscribedUsersByTenantIdWithPagination(@Param("tenantId") String tenantId, Pageable pageable);

    // Multi-tenant support: Find user by Clerk User ID (globally unique across all tenants)
    Optional<UserProfile> findByClerkUserId(String clerkUserId);

    // Multi-tenant support: Find user by Clerk User ID and tenant
    Optional<UserProfile> findByClerkUserIdAndTenantId(String clerkUserId, String tenantId);

    // Multi-tenant support: Check if user with Clerk ID exists
    boolean existsByClerkUserId(String clerkUserId);

    boolean existsByUserRole(String userRole);

    boolean existsByUserRoleAndTenantId(String userRole, String tenantId);
}
