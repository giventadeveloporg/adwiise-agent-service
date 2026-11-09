package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchoolFeeInvoice.
 */
@Entity
@Table(name = "school_fee_invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolFeeInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schoolFeeInvoiceSeq")
    @SequenceGenerator(name = "schoolFeeInvoiceSeq", sequenceName = "public.school_fee_invoice_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Size(max = 32)
    @Column(name = "academic_year", length = 32)
    private String academicYear;

    @NotNull
    @Size(max = 32)
    @Column(name = "fee_kind", length = 32, nullable = false)
    private String feeKind;

    @Size(max = 255)
    @Column(name = "student_name", length = 255)
    private String studentName;

    @Size(max = 64)
    @Column(name = "class_label", length = 64)
    private String classLabel;

    @Size(max = 255)
    @Column(name = "payer_name", length = 255)
    private String payerName;

    @Size(max = 255)
    @Column(name = "payer_email", length = 255)
    private String payerEmail;

    @Size(max = 50)
    @Column(name = "payer_phone", length = 50)
    private String payerPhone;

    @NotNull
    @Column(name = "amount_due", nullable = false, precision = 21, scale = 2)
    private BigDecimal amountDue;

    @NotNull
    @Size(max = 3)
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @NotNull
    @Size(max = 16)
    @Column(name = "status", length = 16, nullable = false)
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
        if (!(o instanceof SchoolFeeInvoice)) {
            return false;
        }
        SchoolFeeInvoice other = (SchoolFeeInvoice) o;
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
        return "SchoolFeeInvoice{id=" + id + "}";
    }
}
