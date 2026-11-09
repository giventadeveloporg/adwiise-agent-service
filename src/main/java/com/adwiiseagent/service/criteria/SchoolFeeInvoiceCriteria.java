package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.adwiiseagent.domain.SchoolFeeInvoice} entity.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolFeeInvoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private StringFilter title;
    private StringFilter academicYear;
    private StringFilter feeKind;
    private StringFilter studentName;
    private StringFilter classLabel;
    private StringFilter payerName;
    private StringFilter payerEmail;
    private StringFilter payerPhone;
    private BigDecimalFilter amountDue;
    private StringFilter currency;
    private LocalDateFilter dueDate;
    private StringFilter status;
    private Boolean distinct;

    public SchoolFeeInvoiceCriteria() {}

    public SchoolFeeInvoiceCriteria(SchoolFeeInvoiceCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.title = other.optionalTitle().map(StringFilter::copy).orElse(null);
        this.academicYear = other.optionalAcademicYear().map(StringFilter::copy).orElse(null);
        this.feeKind = other.optionalFeeKind().map(StringFilter::copy).orElse(null);
        this.studentName = other.optionalStudentName().map(StringFilter::copy).orElse(null);
        this.classLabel = other.optionalClassLabel().map(StringFilter::copy).orElse(null);
        this.payerName = other.optionalPayerName().map(StringFilter::copy).orElse(null);
        this.payerEmail = other.optionalPayerEmail().map(StringFilter::copy).orElse(null);
        this.payerPhone = other.optionalPayerPhone().map(StringFilter::copy).orElse(null);
        this.amountDue = other.optionalAmountDue().map(BigDecimalFilter::copy).orElse(null);
        this.currency = other.optionalCurrency().map(StringFilter::copy).orElse(null);
        this.dueDate = other.optionalDueDate().map(LocalDateFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SchoolFeeInvoiceCriteria copy() {
        return new SchoolFeeInvoiceCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public Optional<StringFilter> optionalTitle() {
        return Optional.ofNullable(title);
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getAcademicYear() {
        return academicYear;
    }

    public Optional<StringFilter> optionalAcademicYear() {
        return Optional.ofNullable(academicYear);
    }

    public void setAcademicYear(StringFilter academicYear) {
        this.academicYear = academicYear;
    }

    public StringFilter getFeeKind() {
        return feeKind;
    }

    public Optional<StringFilter> optionalFeeKind() {
        return Optional.ofNullable(feeKind);
    }

    public void setFeeKind(StringFilter feeKind) {
        this.feeKind = feeKind;
    }

    public StringFilter getStudentName() {
        return studentName;
    }

    public Optional<StringFilter> optionalStudentName() {
        return Optional.ofNullable(studentName);
    }

    public void setStudentName(StringFilter studentName) {
        this.studentName = studentName;
    }

    public StringFilter getClassLabel() {
        return classLabel;
    }

    public Optional<StringFilter> optionalClassLabel() {
        return Optional.ofNullable(classLabel);
    }

    public void setClassLabel(StringFilter classLabel) {
        this.classLabel = classLabel;
    }

    public StringFilter getPayerName() {
        return payerName;
    }

    public Optional<StringFilter> optionalPayerName() {
        return Optional.ofNullable(payerName);
    }

    public void setPayerName(StringFilter payerName) {
        this.payerName = payerName;
    }

    public StringFilter getPayerEmail() {
        return payerEmail;
    }

    public Optional<StringFilter> optionalPayerEmail() {
        return Optional.ofNullable(payerEmail);
    }

    public void setPayerEmail(StringFilter payerEmail) {
        this.payerEmail = payerEmail;
    }

    public StringFilter getPayerPhone() {
        return payerPhone;
    }

    public Optional<StringFilter> optionalPayerPhone() {
        return Optional.ofNullable(payerPhone);
    }

    public void setPayerPhone(StringFilter payerPhone) {
        this.payerPhone = payerPhone;
    }

    public BigDecimalFilter getAmountDue() {
        return amountDue;
    }

    public Optional<BigDecimalFilter> optionalAmountDue() {
        return Optional.ofNullable(amountDue);
    }

    public void setAmountDue(BigDecimalFilter amountDue) {
        this.amountDue = amountDue;
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

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public Optional<LocalDateFilter> optionalDueDate() {
        return Optional.ofNullable(dueDate);
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
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
        if (!(o instanceof SchoolFeeInvoiceCriteria)) {
            return false;
        }
        SchoolFeeInvoiceCriteria other = (SchoolFeeInvoiceCriteria) o;
        return (
            Objects.equals(id, other.id) &&
            Objects.equals(tenantId, other.tenantId) &&
            Objects.equals(title, other.title) &&
            Objects.equals(academicYear, other.academicYear) &&
            Objects.equals(feeKind, other.feeKind) &&
            Objects.equals(studentName, other.studentName) &&
            Objects.equals(classLabel, other.classLabel) &&
            Objects.equals(payerName, other.payerName) &&
            Objects.equals(payerEmail, other.payerEmail) &&
            Objects.equals(payerPhone, other.payerPhone) &&
            Objects.equals(amountDue, other.amountDue) &&
            Objects.equals(currency, other.currency) &&
            Objects.equals(dueDate, other.dueDate) &&
            Objects.equals(status, other.status) &&
            Objects.equals(distinct, other.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tenantId,
            title,
            academicYear,
            feeKind,
            studentName,
            classLabel,
            payerName,
            payerEmail,
            payerPhone,
            amountDue,
            currency,
            dueDate,
            status,
            distinct
        );
    }
}
