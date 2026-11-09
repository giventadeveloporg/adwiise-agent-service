package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchoolHighlight.
 */
@Entity
@Table(name = "school_highlight")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolHighlight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schoolHighlightSeq")
    @SequenceGenerator(name = "schoolHighlightSeq", sequenceName = "public.school_highlight_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    @Column(name = "person_name", length = 255, nullable = false)
    private String personName;

    @Size(max = 255)
    @Column(name = "subtitle", length = 255)
    private String subtitle;

    @Size(max = 1024)
    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    @Size(max = 32)
    @Column(name = "academic_year", length = 32)
    private String academicYear;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_active")
    private Boolean isActive;

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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolHighlight)) {
            return false;
        }
        SchoolHighlight other = (SchoolHighlight) o;
        return (
            Objects.equals(id, other.id) &&
            Objects.equals(tenantId, other.tenantId) &&
            Objects.equals(personName, other.personName) &&
            Objects.equals(subtitle, other.subtitle) &&
            Objects.equals(imageUrl, other.imageUrl) &&
            Objects.equals(academicYear, other.academicYear) &&
            Objects.equals(sortOrder, other.sortOrder) &&
            Objects.equals(isActive, other.isActive)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantId, personName, subtitle, imageUrl, academicYear, sortOrder, isActive);
    }

    @Override
    public String toString() {
        return "SchoolHighlight{id=" + id + "}";
    }
}
