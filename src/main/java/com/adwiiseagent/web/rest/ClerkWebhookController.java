package com.adwiiseagent.web.rest;

import com.adwiiseagent.domain.ClerkUserTenant;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.ClerkProfileIdentityService;
import com.adwiiseagent.service.ClerkUserTenantService;
import com.adwiiseagent.service.WebhookEventHandlerService;
import com.adwiiseagent.service.WebhookSignatureService;
import com.adwiiseagent.service.dto.ClerkWebhookRequest;
import com.adwiiseagent.service.dto.WebhookResponse;
import com.adwiiseagent.service.webhook.InboundWebhookGuard;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for receiving Clerk webhook events and user synchronization.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Clerk Webhooks", description = "Endpoints for Clerk webhooks and user sync")
public class ClerkWebhookController {

    private final Logger log = LoggerFactory.getLogger(ClerkWebhookController.class);

    private final WebhookSignatureService webhookSignatureService;
    private final WebhookEventHandlerService webhookEventHandlerService;
    private final ClerkProfileIdentityService clerkProfileIdentityService;
    private final ClerkUserTenantService clerkUserTenantService;
    private final ObjectMapper objectMapper;
    private final InboundWebhookGuard inboundWebhookGuard;

    public ClerkWebhookController(
        WebhookSignatureService webhookSignatureService,
        WebhookEventHandlerService webhookEventHandlerService,
        ClerkProfileIdentityService clerkProfileIdentityService,
        ClerkUserTenantService clerkUserTenantService,
        ObjectMapper objectMapper,
        InboundWebhookGuard inboundWebhookGuard
    ) {
        this.webhookSignatureService = webhookSignatureService;
        this.webhookEventHandlerService = webhookEventHandlerService;
        this.clerkProfileIdentityService = clerkProfileIdentityService;
        this.clerkUserTenantService = clerkUserTenantService;
        this.objectMapper = objectMapper;
        this.inboundWebhookGuard = inboundWebhookGuard;
    }

    /**
     * POST /api/webhooks/clerk : Receive Clerk webhook events
     *
     * @param payload   the raw webhook payload
     * @param signature the X-Clerk-Signature header
     * @return the ResponseEntity with status 200 (OK) and processing result
     */
    @PostMapping("/clerk")
    @Operation(summary = "Receive Clerk webhook", description = "Endpoint for receiving webhook events from Clerk")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Webhook processed successfully",
                content = @Content(schema = @Schema(implementation = WebhookResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Invalid signature"),
            @ApiResponse(responseCode = "400", description = "Invalid payload"),
        }
    )
    public ResponseEntity<WebhookResponse> handleClerkWebhook(
        @RequestBody String payload,
        @RequestHeader(value = "X-Clerk-Signature", required = false) String signature
    ) {
        if (!inboundWebhookGuard.isClerkInboundEnabled()) {
            log.info("Clerk inbound webhooks are disabled; rejecting request");
            WebhookResponse disabledResponse = new WebhookResponse();
            disabledResponse.setSuccess(false);
            disabledResponse.setMessage("Clerk inbound webhooks are disabled");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(disabledResponse);
        }

        long startTime = System.currentTimeMillis();
        log.info("Received Clerk webhook");

        WebhookResponse response = new WebhookResponse();

        try {
            // Verify signature
            if (signature == null || !webhookSignatureService.verifyWebhookSignature(payload, signature)) {
                log.warn("Invalid webhook signature");
                response.setSuccess(false);
                response.setMessage("Invalid signature");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Parse payload
            ClerkWebhookRequest webhookRequest = objectMapper.readValue(payload, ClerkWebhookRequest.class);

            log.info("Processing webhook event: {} (ID: {})", webhookRequest.getType(), webhookRequest.getId());

            // Check for duplicate/already processed events
            if (webhookEventHandlerService.isEventProcessed(webhookRequest.getId())) {
                log.info("Event {} already processed, skipping", webhookRequest.getId());
                response.setSuccess(true);
                response.setMessage("Event already processed");
                response.setEventId(webhookRequest.getId());
                response.setProcessingTimeMs(System.currentTimeMillis() - startTime);
                return ResponseEntity.ok(response);
            }

            // Route to appropriate handler
            webhookEventHandlerService.handleEvent(webhookRequest);

            // Prepare success response
            response.setSuccess(true);
            response.setMessage("Webhook processed successfully");
            response.setEventId(webhookRequest.getId());
            response.setProcessingTimeMs(System.currentTimeMillis() - startTime);

            log.info("Webhook processed successfully in {}ms", response.getProcessingTimeMs());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing webhook", e);
            response.setSuccess(false);
            response.setMessage("Error processing webhook: " + e.getMessage());
            response.setProcessingTimeMs(System.currentTimeMillis() - startTime);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * POST /api/clerk/sync-user : Sync Clerk user to backend multi-tenant system
     *
     * @param userData Map containing clerkUserId, email, firstName, lastName, tenantId
     * @return 200 OK if sync successful
     */
    @PostMapping("/clerk/sync-user")
    @Operation(summary = "Sync Clerk user", description = "Syncs Clerk-authenticated user to multi-tenant system")
    public ResponseEntity<Map<String, Object>> syncClerkUser(@RequestBody Map<String, Object> userData) {
        try {
            String clerkUserId = (String) userData.get("clerkUserId");
            String email = (String) userData.get("email");
            String firstName = (String) userData.get("firstName");
            String lastName = (String) userData.get("lastName");
            String tenantId = (String) userData.get("tenantId");

            log.info("Syncing Clerk user {} to tenant {}", email, tenantId);

            // Get or create user profile with tenant ID
            UserProfile userProfile = getOrCreateUserProfile(clerkUserId, email, firstName, lastName, tenantId);

            // Get or create tenant membership
            ClerkUserTenant tenantMembership = clerkUserTenantService.getOrCreateMembership(userProfile, tenantId, "member");

            log.info("User {} synced successfully to tenant {} with role: {}", userProfile.getId(), tenantId, tenantMembership.getRole());

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", true);
            responseMap.put("userId", userProfile.getId());
            responseMap.put("tenantId", tenantId);
            responseMap.put("role", tenantMembership.getRole());

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            log.error("Error syncing Clerk user", e);
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("success", false);
            errorMap.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorMap);
        }
    }

    /**
     * Get or create UserProfile for Clerk user.
     * Implements multi-tenant shared user pool pattern.
     * CRITICAL: Always sets tenantId on new profiles to comply with multi-tenant architecture.
     */
    private UserProfile getOrCreateUserProfile(String clerkUserId, String email, String firstName, String lastName, String tenantId) {
        log.debug("Getting or creating UserProfile for Clerk user ID: {} with tenantId: {}", clerkUserId, tenantId);

        return clerkProfileIdentityService
            .resolve(clerkUserId, tenantId, email, firstName, lastName, null, "clerk")
            .profile();
    }
}
