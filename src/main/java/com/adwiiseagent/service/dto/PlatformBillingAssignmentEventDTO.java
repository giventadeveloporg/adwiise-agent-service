package com.adwiiseagent.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class PlatformBillingAssignmentEventDTO implements Serializable {

    private Long id;
    private Long organizationId;
    private String tenantId;
    private String product;
    private String previousPlanCode;
    private String newPlanCode;
    private BigDecimal previousAmountUsd;
    private BigDecimal newAmountUsd;
    private String actorUserId;
    private String reason;
    private ZonedDateTime occurredAt;
    private ZonedDateTime createdAt;

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
