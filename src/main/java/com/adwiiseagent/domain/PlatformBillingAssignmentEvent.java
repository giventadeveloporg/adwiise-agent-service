package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "platform_billing_assignment_event")
public class PlatformBillingAssignmentEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platformBillingAssignmentEventSeq")
    @SequenceGenerator(
        name = "platformBillingAssignmentEventSeq",
        sequenceName = "public.platform_billing_assignment_event_id_seq",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "organization_id")
    private Long organizationId;

    @Size(max = 255)
    @Column(name = "tenant_id", length = 255)
    private String tenantId;

    @Size(max = 32)
    @Column(name = "product", length = 32)
    private String product;

    @Size(max = 64)
    @Column(name = "previous_plan_code", length = 64)
    private String previousPlanCode;

    @Size(max = 64)
    @Column(name = "new_plan_code", length = 64)
    private String newPlanCode;

    @Column(name = "previous_amount_usd", precision = 21, scale = 2)
    private BigDecimal previousAmountUsd;

    @Column(name = "new_amount_usd", precision = 21, scale = 2)
    private BigDecimal newAmountUsd;

    @Size(max = 255)
    @Column(name = "actor_user_id", length = 255)
    private String actorUserId;

    @Size(max = 2000)
    @Column(name = "reason", length = 2000)
    private String reason;

    @NotNull
    @Column(name = "occurred_at", nullable = false)
    private ZonedDateTime occurredAt;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    public void prePersist() {
        ZonedDateTime now = ZonedDateTime.now();
        if (occurredAt == null) occurredAt = now;
        if (createdAt == null) createdAt = now;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPreviousPlanCode() {
        return previousPlanCode;
    }

    public void setPreviousPlanCode(String previousPlanCode) {
        this.previousPlanCode = previousPlanCode;
    }

    public String getNewPlanCode() {
        return newPlanCode;
    }

    public void setNewPlanCode(String newPlanCode) {
        this.newPlanCode = newPlanCode;
    }

    public BigDecimal getPreviousAmountUsd() {
        return previousAmountUsd;
    }

    public void setPreviousAmountUsd(BigDecimal previousAmountUsd) {
        this.previousAmountUsd = previousAmountUsd;
    }

    public BigDecimal getNewAmountUsd() {
        return newAmountUsd;
    }

    public void setNewAmountUsd(BigDecimal newAmountUsd) {
        this.newAmountUsd = newAmountUsd;
    }

    public String getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(String actorUserId) {
        this.actorUserId = actorUserId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(ZonedDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
