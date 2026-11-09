package com.adwiiseagent.web.rest;

import com.adwiiseagent.domain.PaymentGatewayCatalog;
import com.adwiiseagent.repository.PaymentGatewayCatalogRepository;
import com.adwiiseagent.service.dto.PaymentGatewayCatalogDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-gateways")
public class PaymentGatewayCatalogResource {

    private final PaymentGatewayCatalogRepository repository;

    public PaymentGatewayCatalogResource(PaymentGatewayCatalogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<List<PaymentGatewayCatalogDTO>> list() {
        List<PaymentGatewayCatalogDTO> rows = repository.findByIsActiveTrueOrderBySortOrderAscLabelAsc().stream().map(this::toDto).toList();
        return ResponseEntity.ok(rows);
    }

    @PostMapping("")
    public ResponseEntity<PaymentGatewayCatalogDTO> create(@Valid @RequestBody PaymentGatewayCatalogDTO dto) {
        PaymentGatewayCatalog row = new PaymentGatewayCatalog();
        copy(dto, row);
        return ResponseEntity.ok(toDto(repository.save(row)));
    }

    private void copy(PaymentGatewayCatalogDTO dto, PaymentGatewayCatalog row) {
        row.setTenantId(dto.getTenantId());
        row.setCode(dto.getCode() == null ? null : dto.getCode().trim().toUpperCase());
        row.setLabel(dto.getLabel());
        row.setHint(dto.getHint());
        row.setCheckoutMode(dto.getCheckoutMode() == null ? "CLIENT_SDK" : dto.getCheckoutMode());
        row.setClientScriptUrl(dto.getClientScriptUrl());
        row.setSignatureHeader(dto.getSignatureHeader());
        row.setSortOrder(dto.getSortOrder() == null ? 0 : dto.getSortOrder());
        row.setIsActive(dto.getIsActive() == null || dto.getIsActive());
    }

    private PaymentGatewayCatalogDTO toDto(PaymentGatewayCatalog row) {
        PaymentGatewayCatalogDTO dto = new PaymentGatewayCatalogDTO();
        dto.setId(row.getId());
        dto.setTenantId(row.getTenantId());
        dto.setCode(row.getCode());
        dto.setLabel(row.getLabel());
        dto.setHint(row.getHint());
        dto.setCheckoutMode(row.getCheckoutMode());
        dto.setClientScriptUrl(row.getClientScriptUrl());
        dto.setSignatureHeader(row.getSignatureHeader());
        dto.setSortOrder(row.getSortOrder());
        dto.setIsActive(row.getIsActive());
        return dto;
    }
}
