package com.adwiiseagent.repository;

import com.adwiiseagent.domain.EventTicketTransaction;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventTicketTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventTicketTransactionRepository
    extends JpaRepository<EventTicketTransaction, Long>, JpaSpecificationExecutor<EventTicketTransaction> {
    /**
     * Find a ticket transaction by Stripe payment intent ID.
     *
     * @param stripePaymentIntentId the Stripe payment intent ID
     * @return Optional containing the ticket transaction if found
     */
    Optional<EventTicketTransaction> findByStripePaymentIntentId(String stripePaymentIntentId);

    /**
     * Find a ticket transaction by Stripe payment intent ID and tenant ID.
     * This ensures tenant isolation when checking for duplicates.
     *
     * @param stripePaymentIntentId the Stripe payment intent ID
     * @param tenantId the tenant ID
     * @return Optional containing the ticket transaction if found for the specific tenant
     */
    Optional<EventTicketTransaction> findByStripePaymentIntentIdAndTenantId(String stripePaymentIntentId, String tenantId);

    /**
     * Check if a ticket transaction exists for a given Stripe payment intent ID.
     * This is a global check across all tenants to prevent any duplicates.
     *
     * @param stripePaymentIntentId the Stripe payment intent ID
     * @return true if a transaction exists for this payment intent
     */
    boolean existsByStripePaymentIntentId(String stripePaymentIntentId);

    /**
     * Find all ticket transactions by Stripe payment intent ID.
     * Used for debugging and detecting cross-tenant duplicates.
     *
     * @param stripePaymentIntentId the Stripe payment intent ID
     * @return List of all ticket transactions with this payment intent ID
     */
    List<EventTicketTransaction> findAllByStripePaymentIntentId(String stripePaymentIntentId);

    /**
     * Find ticket transactions by transaction reference (payment transaction ID).
     * This is used as a fallback when stripePaymentIntentId lookup fails.
     *
     * @param transactionReference the transaction reference (payment transaction ID as string)
     * @return List of ticket transactions matching the transaction reference
     */
    List<EventTicketTransaction> findByTransactionReference(String transactionReference);

    /**
     * Claim a ticket confirmation send. A second caller updates zero rows when the timestamp is already set.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        "UPDATE EventTicketTransaction t SET t.confirmationSentAt = :sentAt, t.updatedAt = :sentAt WHERE t.id = :id AND t.confirmationSentAt IS NULL"
    )
    int claimConfirmationSend(@Param("id") Long id, @Param("sentAt") ZonedDateTime sentAt);

    /**
     * Clear a claim when the batch job does not accept the send, so a later call can try again.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        "UPDATE EventTicketTransaction t SET t.confirmationSentAt = NULL, t.updatedAt = :updatedAt WHERE t.id = :id AND t.confirmationSentAt = :sentAt"
    )
    int releaseConfirmationSend(@Param("id") Long id, @Param("sentAt") ZonedDateTime sentAt, @Param("updatedAt") ZonedDateTime updatedAt);
}
