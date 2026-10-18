package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TenantTournament;
import com.adwiiseagent.repository.TenantTournamentRepository;
import com.adwiiseagent.service.S3Service;
import com.adwiiseagent.service.TenantTournamentService;
import com.adwiiseagent.service.dto.TenantTournamentDTO;
import com.adwiiseagent.service.mapper.TenantTournamentMapper;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class TenantTournamentServiceImpl implements TenantTournamentService {

    private static final Logger log = LoggerFactory.getLogger(TenantTournamentServiceImpl.class);
    private static final Pattern SLUG_PATTERN = Pattern.compile("^[a-z0-9-]+$");
    private static final Set<String> STATUSES = Set.of("UPCOMING", "IN_PROGRESS", "COMPLETED");
    private static final Set<String> IMAGE_KINDS = Set.of("hero", "fixtures", "chief-guest");

    private final TenantTournamentRepository tenantTournamentRepository;
    private final TenantTournamentMapper tenantTournamentMapper;
    private final S3Service s3Service;

    public TenantTournamentServiceImpl(
        TenantTournamentRepository tenantTournamentRepository,
        TenantTournamentMapper tenantTournamentMapper,
        S3Service s3Service
    ) {
        this.tenantTournamentRepository = tenantTournamentRepository;
        this.tenantTournamentMapper = tenantTournamentMapper;
        this.s3Service = s3Service;
    }

    @Override
    public TenantTournamentDTO save(TenantTournamentDTO dto) {
        log.debug("Request to save TenantTournament : {}", dto);
        prepare(dto, true);
        TenantTournament entity = tenantTournamentMapper.toEntity(dto);
        if (Boolean.TRUE.equals(entity.getShowInHeader())) {
            releaseHeaderSlot(entity.getTenantId(), null);
        }
        return tenantTournamentMapper.toDto(persist(entity, dto));
    }

    @Override
    public TenantTournamentDTO update(TenantTournamentDTO dto) {
        log.debug("Request to update TenantTournament : {}", dto);
        prepare(dto, false);
        if (Boolean.TRUE.equals(dto.getShowInHeader())) {
            releaseHeaderSlot(dto.getTenantId(), dto.getId());
        }
        TenantTournament entity = tenantTournamentMapper.toEntity(dto);
        return tenantTournamentMapper.toDto(persist(entity, dto));
    }

    @Override
    public Optional<TenantTournamentDTO> partialUpdate(TenantTournamentDTO dto) {
        log.debug("Request to partially update TenantTournament : {}", dto);
        if (dto.getSlug() != null) {
            validateSlug(dto.getSlug());
        }
        if (dto.getShortLabel() != null) {
            dto.setShortLabel(normalizeShortLabel(dto.getShortLabel()));
        }
        if (dto.getStatus() != null) {
            validateStatus(dto.getStatus());
        }
        return tenantTournamentRepository
            .findById(dto.getId())
            .map(existing -> {
                if (Boolean.TRUE.equals(dto.getShowInHeader())) {
                    releaseHeaderSlot(existing.getTenantId(), existing.getId());
                }
                tenantTournamentMapper.partialUpdate(existing, dto);
                if (existing.getShowInHeader() == null) {
                    existing.setShowInHeader(false);
                }
                if (existing.getIsPublished() == null) {
                    existing.setIsPublished(false);
                }
                return persist(existing, dto);
            })
            .map(tenantTournamentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantTournamentDTO> findAll(Pageable pageable) {
        return tenantTournamentRepository.findAll(pageable).map(tenantTournamentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantTournamentDTO> findOne(Long id) {
        return tenantTournamentRepository.findById(id).map(tenantTournamentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantTournamentDTO> findByTenantIdAndSlug(String tenantId, String slug) {
        return tenantTournamentRepository.findByTenantIdAndSlug(tenantId, slug).map(tenantTournamentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        tenantTournamentRepository.deleteById(id);
    }

    @Override
    public String uploadImage(String tenantId, String kind, MultipartFile file) {
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("Tenant ID is required");
        }
        if (kind == null || !IMAGE_KINDS.contains(kind)) {
            throw new IllegalArgumentException("Image kind must be hero, fixtures, or chief-guest");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String lowerFilename = filename.toLowerCase();
            boolean isValidImageType =
                lowerFilename.endsWith(".png") ||
                lowerFilename.endsWith(".jpg") ||
                lowerFilename.endsWith(".jpeg") ||
                lowerFilename.endsWith(".gif") ||
                lowerFilename.endsWith(".webp");
            if (!isValidImageType) {
                throw new IllegalArgumentException("File must be an image (PNG, JPG, JPEG, GIF, or WEBP)");
            }
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("File size must be less than 10MB");
        }
        String s3Path = s3Service.generateTenantTournamentImagePath(tenantId, kind, file.getOriginalFilename());
        String s3Url = s3Service.uploadFile(s3Path, file);
        log.debug("Uploaded tournament {} image for tenant {} to {}", kind, tenantId, s3Url);
        return s3Url;
    }

    private void prepare(TenantTournamentDTO dto, boolean creating) {
        dto.setShortLabel(normalizeShortLabel(dto.getShortLabel()));
        validateSlug(dto.getSlug());
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            dto.setStatus("UPCOMING");
        }
        validateStatus(dto.getStatus());
        if (dto.getShowInHeader() == null) {
            dto.setShowInHeader(false);
        }
        if (dto.getIsPublished() == null) {
            dto.setIsPublished(false);
        }
        Instant now = Instant.now();
        if (creating || dto.getCreatedAt() == null) {
            dto.setCreatedAt(now);
        }
        dto.setUpdatedAt(now);
    }

    private TenantTournament persist(TenantTournament entity, TenantTournamentDTO dto) {
        try {
            return tenantTournamentRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to save TenantTournament: {}", e.getMessage());
            throw new IllegalArgumentException(
                "A tournament with this short label or slug already exists for this tenant, or another tournament is already shown in the header. Label: " +
                dto.getShortLabel()
            );
        }
    }

    /**
     * Turn off every other header tournament and flush that update before this row is set true.
     * The unique index is checked per statement, so the previous header row must be cleared first.
     */
    private void releaseHeaderSlot(String tenantId, Long keepId) {
        if (tenantId == null || tenantId.isBlank()) {
            return;
        }
        if (keepId == null) {
            tenantTournamentRepository.clearHeaderForTenant(tenantId);
        } else {
            tenantTournamentRepository.clearHeaderExcept(tenantId, keepId);
        }
        tenantTournamentRepository.flush();
    }

    private String normalizeShortLabel(String shortLabel) {
        String label = shortLabel == null ? "" : shortLabel.trim();
        if (label.isEmpty()) {
            throw new IllegalArgumentException("Short label cannot be empty");
        }
        if (label.length() > 40) {
            throw new IllegalArgumentException("Short label must be 40 characters or fewer");
        }
        return label;
    }

    private void validateSlug(String slug) {
        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("Slug cannot be empty");
        }
        if (!SLUG_PATTERN.matcher(slug).matches()) {
            throw new IllegalArgumentException("Slug must contain only lowercase letters, numbers, and hyphens. Invalid slug: " + slug);
        }
    }

    private void validateStatus(String status) {
        if (!STATUSES.contains(status)) {
            throw new IllegalArgumentException("Status must be UPCOMING, IN_PROGRESS, or COMPLETED");
        }
    }
}
