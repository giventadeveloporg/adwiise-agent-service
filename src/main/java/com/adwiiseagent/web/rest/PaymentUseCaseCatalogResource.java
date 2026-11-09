package com.adwiiseagent.web.rest;

import com.adwiiseagent.domain.PaymentUseCaseCatalog;
import com.adwiiseagent.repository.PaymentUseCaseCatalogRepository;
import com.adwiiseagent.service.dto.PaymentUseCaseCatalogDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-use-cases")
public class PaymentUseCaseCatalogResource {

    private final PaymentUseCaseCatalogRepository repository;

    public PaymentUseCaseCatalogResource(PaymentUseCaseCatalogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<List<PaymentUseCaseCatalogDTO>> list() {
        List<PaymentUseCaseCatalogDTO> rows = repository.findByIsActiveTrueOrderBySortOrderAscLabelAsc().stream().map(this::toDto).toList();
        return ResponseEntity.ok(rows);
    }

    private PaymentUseCaseCatalogDTO toDto(PaymentUseCaseCatalog row) {
        PaymentUseCaseCatalogDTO dto = new PaymentUseCaseCatalogDTO();
        dto.setId(row.getId());
        dto.setTenantId(row.getTenantId());
        dto.setCode(row.getCode());
        dto.setLabel(row.getLabel());
        dto.setHint(row.getHint());
        dto.setSortOrder(row.getSortOrder());
        dto.setIsActive(row.getIsActive());
        return dto;
    }
}
