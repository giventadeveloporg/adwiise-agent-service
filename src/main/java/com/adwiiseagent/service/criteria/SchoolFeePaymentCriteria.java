package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.adwiiseagent.domain.SchoolFeePayment} entity.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolFeePaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter invoiceId;
    private StringFilter providerName;
    private StringFilter providerOrderId;
    private StringFilter providerPaymentId;
    private BigDecimalFilter amount;
    private StringFilter currency;
    private StringFilter status;
    private InstantFilter paidAt;
    private Boolean distinct;

    public SchoolFeePaymentCriteria() {}

    public SchoolFeePaymentCriteria(SchoolFeePaymentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.invoiceId = other.optionalInvoiceId().map(LongFilter::copy).orElse(null);
        this.providerName = other.optionalProviderName().map(StringFilter::copy).orElse(null);
        this.providerOrderId = other.optionalProviderOrderId().map(StringFilter::copy).orElse(null);
        this.providerPaymentId = other.optionalProviderPaymentId().map(StringFilter::copy).orElse(null);
        this.amount = other.optionalAmount().map(BigDecimalFilter::copy).orElse(null);
        this.currency = other.optionalCurrency().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.paidAt = other.optionalPaidAt().map(InstantFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SchoolFeePaymentCriteria copy() {
        return new SchoolFeePaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public Optional<StringFilter> optionalTenantId() {
        return Optional.ofNullable(tenantId);
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public Optional<LongFilter> optionalInvoiceId() {
        return Optional.ofNullable(invoiceId);
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public StringFilter getProviderName() {
        return providerName;
    }

    public Optional<StringFilter> optionalProviderName() {
        return Optional.ofNullable(providerName);
    }

    public void setProviderName(StringFilter providerName) {
        this.providerName = providerName;
    }

    public StringFilter getProviderOrderId() {
        return providerOrderId;
    }

    public Optional<StringFilter> optionalProviderOrderId() {
        return Optional.ofNullable(providerOrderId);
    }

    public void setProviderOrderId(StringFilter providerOrderId) {
        this.providerOrderId = providerOrderId;
    }

    public StringFilter getProviderPaymentId() {
        return providerPaymentId;
    }

    public Optional<StringFilter> optionalProviderPaymentId() {
        return Optional.ofNullable(providerPaymentId);
    }

    public void setProviderPaymentId(StringFilter providerPaymentId) {
        this.providerPaymentId = providerPaymentId;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public Optional<BigDecimalFilter> optionalAmount() {
        return Optional.ofNullable(amount);
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getCurrency() {
        return currency;
    }

    public Optional<StringFilter> optionalCurrency() {
        return Optional.ofNullable(currency);
    }

    public void setCurrency(StringFilter currency) {
        this.currency = currency;
    }

    public StringFilter getStatus() {
        return status;
    }

    public Optional<StringFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getPaidAt() {
        return paidAt;
    }

    public Optional<InstantFilter> optionalPaidAt() {
        return Optional.ofNullable(paidAt);
    }

    public void setPaidAt(InstantFilter paidAt) {
        this.paidAt = paidAt;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolFeePaymentCriteria)) {
            return false;
        }
        SchoolFeePaymentCriteria other = (SchoolFeePaymentCriteria) o;
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
            Objects.equals(paidAt, other.paidAt) &&
            Objects.equals(distinct, other.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tenantId,
            invoiceId,
            providerName,
            providerOrderId,
            providerPaymentId,
            amount,
            currency,
            status,
            paidAt,
            distinct
        );
    }
}
