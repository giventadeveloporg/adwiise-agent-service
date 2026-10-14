package com.adwiiseagent.service;

import com.adwiiseagent.service.criteria.UserProfileCriteria;
import com.adwiiseagent.service.dto.AdminUserResponse;
import com.adwiiseagent.service.dto.UpdateRoleRequest;
import com.adwiiseagent.service.dto.UpdateStatusRequest;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for admin operations on users.
 */
public interface AdminService {
    /**
     * List all users with pagination and filtering.
     *
     * @param criteria         the filtering criteria
     * @param pageable         the pagination information
     * @param adminClerkUserId the admin's Clerk user ID
     * @param tenantId         the tenant ID
     * @return paginated list of user profiles
     */
    Page<UserProfileDTO> listUsers(UserProfileCriteria criteria, Pageable pageable, String adminClerkUserId, String tenantId);

    /**
     * Update user role (admin operation).
     *
     * @param userId           the user profile ID
     * @param request          the role update request
     * @param adminClerkUserId the admin's Clerk user ID
     * @param tenantId         the tenant ID
     * @return the update response
     */
    AdminUserResponse updateUserRole(Long userId, UpdateRoleRequest request, String adminClerkUserId, String tenantId);

    /**
     * Update user status (admin operation).
     *
     * @param userId           the user profile ID
     * @param request          the status update request
     * @param adminClerkUserId the admin's Clerk user ID
     * @param tenantId         the tenant ID
     * @return the update response
     */
    AdminUserResponse updateUserStatus(Long userId, UpdateStatusRequest request, String adminClerkUserId, String tenantId);
}
