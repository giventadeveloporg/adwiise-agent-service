package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.TenantTournamentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface TenantTournamentService {
    TenantTournamentDTO save(TenantTournamentDTO tenantTournamentDTO);

    TenantTournamentDTO update(TenantTournamentDTO tenantTournamentDTO);

    Optional<TenantTournamentDTO> partialUpdate(TenantTournamentDTO tenantTournamentDTO);

    Page<TenantTournamentDTO> findAll(Pageable pageable);

    Optional<TenantTournamentDTO> findOne(Long id);

    Optional<TenantTournamentDTO> findByTenantIdAndSlug(String tenantId, String slug);

    void delete(Long id);

    /**
     * Upload a tournament image to storage and return its public URL.
     * The caller stores that URL on the tournament row.
     */
    String uploadImage(String tenantId, String kind, MultipartFile file);
}
