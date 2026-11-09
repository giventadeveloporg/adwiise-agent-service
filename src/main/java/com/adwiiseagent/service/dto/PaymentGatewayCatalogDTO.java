package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public class PaymentGatewayCatalogDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 64)
    private String code;

    @NotNull
    @Size(max = 255)
    private String label;

    @Size(max = 255)
    private String hint;

    @Size(max = 32)
    private String checkoutMode;

    @Size(max = 1024)
    private String clientScriptUrl;

    @Size(max = 128)
    private String signatureHeader;

    private Integer sortOrder;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getCheckoutMode() {
        return checkoutMode;
    }

    public void setCheckoutMode(String checkoutMode) {
        this.checkoutMode = checkoutMode;
    }

    public String getClientScriptUrl() {
        return clientScriptUrl;
    }

    public void setClientScriptUrl(String clientScriptUrl) {
        this.clientScriptUrl = clientScriptUrl;
    }

    public String getSignatureHeader() {
        return signatureHeader;
    }

    public void setSignatureHeader(String signatureHeader) {
        this.signatureHeader = signatureHeader;
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
}
