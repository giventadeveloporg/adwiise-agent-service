package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * Write-only credential form. Secret fields are accepted on save and are not returned.
 * The server bundle adds decrypted secrets only on the internal checkout endpoint.
 */
public class PaymentGatewayCredentialDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 64)
    private String providerName;

    @Size(max = 64)
    private String paymentUseCase;

    @Size(max = 500)
    private String publishableKey;

    private String configurationJson;

    private Boolean isActive;

    private Integer fallbackOrder;

    private Boolean apiKeyConfigured;

    private Boolean secretKeyConfigured;

    private Boolean webhookSecretConfigured;

    private String checkoutMode;

    private String clientScriptUrl;

    private String signatureHeader;

    /** Write-only. A blank value keeps the stored ciphertext. */
    private String apiKey;

    /** Write-only. A blank value keeps the stored ciphertext. */
    private String secretKey;

    /** Write-only. A blank value keeps the stored ciphertext. */
    private String webhookSecret;

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

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPaymentUseCase() {
        return paymentUseCase;
    }

    public void setPaymentUseCase(String paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }

    public String getConfigurationJson() {
        return configurationJson;
    }

    public void setConfigurationJson(String configurationJson) {
        this.configurationJson = configurationJson;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getFallbackOrder() {
        return fallbackOrder;
    }

    public void setFallbackOrder(Integer fallbackOrder) {
        this.fallbackOrder = fallbackOrder;
    }

    public Boolean getApiKeyConfigured() {
        return apiKeyConfigured;
    }

    public void setApiKeyConfigured(Boolean apiKeyConfigured) {
        this.apiKeyConfigured = apiKeyConfigured;
    }

    public Boolean getSecretKeyConfigured() {
        return secretKeyConfigured;
    }

    public void setSecretKeyConfigured(Boolean secretKeyConfigured) {
        this.secretKeyConfigured = secretKeyConfigured;
    }

    public Boolean getWebhookSecretConfigured() {
        return webhookSecretConfigured;
    }

    public void setWebhookSecretConfigured(Boolean webhookSecretConfigured) {
        this.webhookSecretConfigured = webhookSecretConfigured;
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getWebhookSecret() {
        return webhookSecret;
    }

    public void setWebhookSecret(String webhookSecret) {
        this.webhookSecret = webhookSecret;
    }
}
