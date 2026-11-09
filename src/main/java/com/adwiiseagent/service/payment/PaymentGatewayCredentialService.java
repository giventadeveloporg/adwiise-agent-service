package com.adwiiseagent.service.payment;

import com.adwiiseagent.domain.PaymentGatewayCatalog;
import com.adwiiseagent.domain.PaymentProviderConfig;
import com.adwiiseagent.repository.PaymentGatewayCatalogRepository;
import com.adwiiseagent.repository.PaymentProviderConfigRepository;
import com.adwiiseagent.service.dto.PaymentGatewayCredentialDTO;
import com.adwiiseagent.service.payment.encryption.PaymentCredentialEncryptionService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentGatewayCredentialService {

    private final PaymentProviderConfigRepository configRepository;
    private final PaymentGatewayCatalogRepository catalogRepository;
    private final PaymentCredentialEncryptionService encryptionService;

    public PaymentGatewayCredentialService(
        PaymentProviderConfigRepository configRepository,
        PaymentGatewayCatalogRepository catalogRepository,
        PaymentCredentialEncryptionService encryptionService
    ) {
        this.configRepository = configRepository;
        this.catalogRepository = catalogRepository;
        this.encryptionService = encryptionService;
    }

    @Transactional(readOnly = true)
    public List<PaymentGatewayCredentialDTO> listForTenant(String tenantId) {
        return configRepository.findByTenantIdOrderByFallbackOrderAscIdAsc(tenantId).stream().map(row -> toPublicView(row, false)).toList();
    }

    /**
     * Decrypted bundle for the Next server that is about to call the gateway.
     * A specific use case wins over a row whose use case is blank.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentGatewayCredentialDTO> findServerBundle(String tenantId, String paymentUseCase) {
        String useCase = blankToNull(paymentUseCase);
        List<PaymentProviderConfig> rows = useCase == null
            ? configRepository.findByTenantIdAndIsActiveTrueOrderByFallbackOrderAsc(tenantId)
            : configRepository.findByTenantIdAndPaymentUseCaseAndIsActiveTrueOrderByFallbackOrderAsc(tenantId, useCase);
        if (useCase != null) {
            List<PaymentProviderConfig> exact = rows
                .stream()
                .filter(row -> useCase.equalsIgnoreCase(row.getPaymentUseCase()))
                .sorted(Comparator.comparing(row -> row.getFallbackOrder() == null ? 0 : row.getFallbackOrder()))
                .toList();
            if (!exact.isEmpty()) {
                rows = exact;
            }
        }
        return rows.stream().findFirst().map(row -> toPublicView(row, true));
    }

    public PaymentGatewayCredentialDTO save(PaymentGatewayCredentialDTO input) {
        String providerName = normalize(input.getProviderName());
        String useCase = blankToNull(input.getPaymentUseCase());
        if (useCase != null) {
            useCase = useCase.toUpperCase();
        }
        PaymentProviderConfig row = configRepository
            .findByTenantIdAndProviderName(input.getTenantId(), providerName)
            .orElseGet(PaymentProviderConfig::new);
        row.setTenantId(input.getTenantId());
        row.setProviderName(providerName);
        row.setPaymentUseCase(useCase);
        row.setPublishableKey(blankToNull(input.getPublishableKey()));
        row.setConfigurationJson(blankToNull(input.getConfigurationJson()));
        row.setIsActive(input.getIsActive() == null || input.getIsActive());
        row.setFallbackOrder(
            input.getFallbackOrder() == null ? (row.getFallbackOrder() == null ? 0 : row.getFallbackOrder()) : input.getFallbackOrder()
        );
        applySecret(input.getApiKey(), row::setProviderApiKeyEncrypted);
        applySecret(input.getSecretKey(), row::setProviderSecretKeyEncrypted);
        applySecret(input.getWebhookSecret(), row::setWebhookSecretEncrypted);
        return toPublicView(configRepository.save(row), false);
    }

    private PaymentGatewayCredentialDTO toPublicView(PaymentProviderConfig row, boolean includeSecrets) {
        PaymentGatewayCredentialDTO dto = new PaymentGatewayCredentialDTO();
        dto.setId(row.getId());
        dto.setTenantId(row.getTenantId());
        dto.setProviderName(row.getProviderName());
        dto.setPaymentUseCase(row.getPaymentUseCase());
        dto.setPublishableKey(row.getPublishableKey());
        dto.setConfigurationJson(row.getConfigurationJson());
        dto.setIsActive(row.getIsActive());
        dto.setFallbackOrder(row.getFallbackOrder());
        dto.setApiKeyConfigured(hasText(row.getProviderApiKeyEncrypted()));
        dto.setSecretKeyConfigured(hasText(row.getProviderSecretKeyEncrypted()));
        dto.setWebhookSecretConfigured(hasText(row.getWebhookSecretEncrypted()));
        catalogRepository
            .findFirstByCodeAndTenantId(row.getProviderName(), row.getTenantId())
            .or(() -> catalogRepository.findFirstByCodeAndTenantIdIsNull(row.getProviderName()))
            .ifPresent(catalog -> copyCatalog(dto, catalog));
        if (includeSecrets) {
            dto.setApiKey(decryptQuietly(row.getProviderApiKeyEncrypted()));
            dto.setSecretKey(decryptQuietly(row.getProviderSecretKeyEncrypted()));
            dto.setWebhookSecret(decryptQuietly(row.getWebhookSecretEncrypted()));
        }
        return dto;
    }

    private void copyCatalog(PaymentGatewayCredentialDTO dto, PaymentGatewayCatalog catalog) {
        dto.setCheckoutMode(catalog.getCheckoutMode());
        dto.setClientScriptUrl(catalog.getClientScriptUrl());
        dto.setSignatureHeader(catalog.getSignatureHeader());
    }

    private void applySecret(String incoming, java.util.function.Consumer<String> setter) {
        if (!hasText(incoming)) {
            return;
        }
        setter.accept(encryptionService.encrypt(incoming.trim()));
    }

    private String decryptQuietly(String ciphertext) {
        if (!hasText(ciphertext)) {
            return null;
        }
        return encryptionService.decrypt(ciphertext);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }

    private static String blankToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
