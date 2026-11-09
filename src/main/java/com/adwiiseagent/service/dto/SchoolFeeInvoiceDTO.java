package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.adwiiseagent.domain.SchoolFeeInvoice} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolFeeInvoiceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 32)
    private String academicYear;

    @NotNull
    @Size(max = 32)
    private String feeKind;

    @Size(max = 255)
    private String studentName;

    @Size(max = 64)
    private String classLabel;

    @Size(max = 255)
    private String payerName;

    @Size(max = 255)
    private String payerEmail;

    @Size(max = 50)
    private String payerPhone;

    @NotNull
    private BigDecimal amountDue;

    @NotNull
    @Size(max = 3)
    private String currency;

    private LocalDate dueDate;

    @NotNull
    @Size(max = 16)
    private String status;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getFeeKind() {
        return feeKind;
    }

    public void setFeeKind(String feeKind) {
        this.feeKind = feeKind;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassLabel() {
        return classLabel;
    }

    public void setClassLabel(String classLabel) {
        this.classLabel = classLabel;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getPayerPhone() {
        return payerPhone;
    }

    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolFeeInvoiceDTO)) {
            return false;
        }
        SchoolFeeInvoiceDTO other = (SchoolFeeInvoiceDTO) o;
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
            Objects.equals(status, other.status)
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
            status
        );
    }

    @Override
    public String toString() {
        return "SchoolFeeInvoiceDTO{id=" + id + "}";
    }
}
