package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchoolFeePayment.
 */
@Entity
@Table(name = "school_fee_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolFeePayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schoolFeePaymentSeq")
    @SequenceGenerator(name = "schoolFeePaymentSeq", sequenceName = "public.school_fee_payment_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Column(name = "invoice_id", nullable = false)
    private Long invoiceId;

    @NotNull
    @Size(max = 50)
    @Column(name = "provider_name", length = 50, nullable = false)
    private String providerName;

    @Size(max = 255)
    @Column(name = "provider_order_id", length = 255)
    private String providerOrderId;

    @Size(max = 255)
    @Column(name = "provider_payment_id", length = 255)
    private String providerPaymentId;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 21, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Size(max = 3)
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @NotNull
    @Size(max = 32)
    @Column(name = "status", length = 32, nullable = false)
    private String status;

    @Column(name = "paid_at")
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
        if (!(o instanceof SchoolFeePayment)) {
            return false;
        }
        SchoolFeePayment other = (SchoolFeePayment) o;
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
        return "SchoolFeePayment{id=" + id + "}";
    }
}
