package com.adwiiseagent.service;

import com.adwiiseagent.domain.PlatformBillingAssignmentEvent;
import com.adwiiseagent.repository.PlatformBillingAssignmentEventRepository;
import com.adwiiseagent.service.dto.PlatformBillingAssignmentEventDTO;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlatformBillingAssignmentEventService {

    private final PlatformBillingAssignmentEventRepository repository;

    public PlatformBillingAssignmentEventService(PlatformBillingAssignmentEventRepository repository) {
        this.repository = repository;
    }

    public PlatformBillingAssignmentEventDTO save(PlatformBillingAssignmentEventDTO dto) {
        PlatformBillingAssignmentEvent entity = new PlatformBillingAssignmentEvent();
        copy(dto, entity, false);
        entity.setId(null);
        return toDto(repository.save(entity));
    }

    public Optional<PlatformBillingAssignmentEventDTO> partialUpdate(PlatformBillingAssignmentEventDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                copy(dto, existing, true);
                return toDto(repository.save(existing));
            });
    }

    @Transactional(readOnly = true)
    public Optional<PlatformBillingAssignmentEventDTO> findOne(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PlatformBillingAssignmentEventDTO toDto(PlatformBillingAssignmentEvent entity) {
        PlatformBillingAssignmentEventDTO dto = new PlatformBillingAssignmentEventDTO();
        dto.setId(entity.getId());
        dto.setOrganizationId(entity.getOrganizationId());
        dto.setTenantId(entity.getTenantId());
        dto.setProduct(entity.getProduct());
        dto.setPreviousPlanCode(entity.getPreviousPlanCode());
        dto.setNewPlanCode(entity.getNewPlanCode());
        dto.setPreviousAmountUsd(entity.getPreviousAmountUsd());
        dto.setNewAmountUsd(entity.getNewAmountUsd());
        dto.setActorUserId(entity.getActorUserId());
        dto.setReason(entity.getReason());
        dto.setOccurredAt(entity.getOccurredAt());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private void copy(PlatformBillingAssignmentEventDTO dto, PlatformBillingAssignmentEvent entity, boolean partial) {
        if (!partial || dto.getOrganizationId() != null) entity.setOrganizationId(dto.getOrganizationId());
        if (!partial || dto.getTenantId() != null) entity.setTenantId(blankToNull(dto.getTenantId()));
        if (!partial || dto.getProduct() != null) entity.setProduct(blankToNull(dto.getProduct()));
        if (!partial || dto.getPreviousPlanCode() != null) entity.setPreviousPlanCode(blankToNull(dto.getPreviousPlanCode()));
        if (!partial || dto.getNewPlanCode() != null) entity.setNewPlanCode(blankToNull(dto.getNewPlanCode()));
        if (!partial || dto.getPreviousAmountUsd() != null) entity.setPreviousAmountUsd(dto.getPreviousAmountUsd());
        if (!partial || dto.getNewAmountUsd() != null) entity.setNewAmountUsd(dto.getNewAmountUsd());
        if (!partial || dto.getActorUserId() != null) entity.setActorUserId(blankToNull(dto.getActorUserId()));
        if (!partial || dto.getReason() != null) entity.setReason(dto.getReason());
        if (!partial || dto.getOccurredAt() != null) entity.setOccurredAt(dto.getOccurredAt());
        if (dto.getCreatedAt() != null) entity.setCreatedAt(dto.getCreatedAt());
    }

    private String blankToNull(String value) {
        if (value == null || value.isBlank()) return null;
        return value;
    }
}
