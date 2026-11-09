package com.adwiiseagent.web.rest;

import com.adwiiseagent.service.dto.PaymentGatewayCredentialDTO;
import com.adwiiseagent.service.payment.PaymentGatewayCredentialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Service-JWT endpoint for the Next checkout server. Not exposed through the browser proxy.
 * A blank paymentUseCase selects the tenant's general gateway. A specific code prefers that use case.
 */
@RestController
@RequestMapping("/api/internal/payment-gateway-bundle")
public class PaymentGatewayBundleResource {

    private final PaymentGatewayCredentialService service;

    public PaymentGatewayBundleResource(PaymentGatewayCredentialService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<PaymentGatewayCredentialDTO> get(
        @RequestParam String tenantId,
        @RequestParam(required = false) String paymentUseCase
    ) {
        return service
            .findServerBundle(tenantId, paymentUseCase)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
