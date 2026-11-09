package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.service.dto.PaymentGatewayCredentialDTO;
import com.adwiiseagent.service.payment.PaymentGatewayCredentialService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Browser-facing credential form. Responses never include decrypted secrets.
 */
@RestController
@RequestMapping("/api/payment-gateway-credentials")
public class PaymentGatewayCredentialResource {

    private static final String ENTITY_NAME = "paymentGatewayCredential";

    private final PaymentGatewayCredentialService service;

    public PaymentGatewayCredentialResource(PaymentGatewayCredentialService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<PaymentGatewayCredentialDTO>> list(@RequestParam String tenantId) {
        return ResponseEntity.ok(service.listForTenant(tenantId));
    }

    @PutMapping("")
    public ResponseEntity<PaymentGatewayCredentialDTO> save(@Valid @RequestBody PaymentGatewayCredentialDTO dto) {
        if (dto.getTenantId() == null || dto.getTenantId().isBlank() || dto.getProviderName() == null || dto.getProviderName().isBlank()) {
            throw new BadRequestAlertException("Tenant and gateway are required", ENTITY_NAME, "required");
        }
        PaymentGatewayCredentialDTO saved = service.save(dto);
        saved.setApiKey(null);
        saved.setSecretKey(null);
        saved.setWebhookSecret(null);
        return ResponseEntity.ok(saved);
    }
}
