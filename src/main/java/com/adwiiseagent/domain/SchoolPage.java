package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchoolPage.
 */
@Entity
@Table(name = "school_page")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolPage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schoolPageSeq")
    @SequenceGenerator(name = "schoolPageSeq", sequenceName = "public.school_page_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Size(max = 128)
    @Column(name = "slug", length = 128, nullable = false)
    private String slug;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Size(max = 1024)
    @Column(name = "summary", length = 1024)
    private String summary;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Size(max = 1024)
    @Column(name = "image_url", length = 1024)
    private String imageUrl;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        if (!(o instanceof SchoolPage)) {
            return false;
        }
        SchoolPage other = (SchoolPage) o;
        return (
            Objects.equals(id, other.id) &&
            Objects.equals(tenantId, other.tenantId) &&
            Objects.equals(slug, other.slug) &&
            Objects.equals(title, other.title) &&
            Objects.equals(summary, other.summary) &&
            Objects.equals(body, other.body) &&
            Objects.equals(imageUrl, other.imageUrl) &&
            Objects.equals(sortOrder, other.sortOrder) &&
            Objects.equals(isActive, other.isActive)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantId, slug, title, summary, body, imageUrl, sortOrder, isActive);
    }

    @Override
    public String toString() {
        return "SchoolPage{id=" + id + "}";
    }
}
