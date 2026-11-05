package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * One tenant site inside an organization portfolio. A tenant_id belongs to at most one portfolio.
 */
@Entity
@Table(
    name = "organization_portfolio_member",
    uniqueConstraints = {
        @UniqueConstraint(name = "ux_organization_portfolio_member__tenant", columnNames = { "tenant_id" }),
        @UniqueConstraint(name = "ux_organization_portfolio_member__portfolio_tenant", columnNames = { "portfolio_id", "tenant_id" }),
    }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationPortfolioMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizationPortfolioMemberSeq")
    @SequenceGenerator(
        name = "organizationPortfolioMemberSeq",
        sequenceName = "public.organization_portfolio_member_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        ZonedDateTime now = ZonedDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
        if (isPrimary == null) {
            isPrimary = false;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationPortfolioMember)) {
            return false;
        }
        return id != null && id.equals(((OrganizationPortfolioMember) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "OrganizationPortfolioMember{id=" +
            id +
            ", portfolioId=" +
            portfolioId +
            ", tenantId='" +
            tenantId +
            "', isPrimary=" +
            isPrimary +
            "}"
        );
    }
}
