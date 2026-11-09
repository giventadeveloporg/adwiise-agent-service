package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.adwiiseagent.domain.SchoolFeePayment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolFeePaymentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long invoiceId;

    @NotNull
    @Size(max = 50)
    private String providerName;

    @Size(max = 255)
    private String providerOrderId;

    @Size(max = 255)
    private String providerPaymentId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Size(max = 3)
    private String currency;

    @NotNull
    @Size(max = 32)
    private String status;

    private Instant paidAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderOrderId() {
        return providerOrderId;
    }

    public void setProviderOrderId(String providerOrderId) {
        this.providerOrderId = providerOrderId;
    }

    public String getProviderPaymentId() {
        return providerPaymentId;
    }

    public void setProviderPaymentId(String providerPaymentId) {
        this.providerPaymentId = providerPaymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolFeePaymentDTO)) {
            return false;
        }
        SchoolFeePaymentDTO other = (SchoolFeePaymentDTO) o;
        return (
            Objects.equals(id, other.id) &&
            Objects.equals(tenantId, other.tenantId) &&
            Objects.equals(invoiceId, other.invoiceId) &&
            Objects.equals(providerName, other.providerName) &&
            Objects.equals(providerOrderId, other.providerOrderId) &&
            Objects.equals(providerPaymentId, other.providerPaymentId) &&
            Objects.equals(amount, other.amount) &&
            Objects.equals(currency, other.currency) &&
            Objects.equals(status, other.status) &&
            Objects.equals(paidAt, other.paidAt)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantId, invoiceId, providerName, providerOrderId, providerPaymentId, amount, currency, status, paidAt);
    }

    @Override
    public String toString() {
        return "SchoolFeePaymentDTO{id=" + id + "}";
    }
}
