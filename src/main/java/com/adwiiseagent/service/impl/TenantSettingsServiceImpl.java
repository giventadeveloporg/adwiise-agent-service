package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.domain.TenantSettings;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.repository.TenantSettingsRepository;
import com.adwiiseagent.service.S3Service;
import com.adwiiseagent.service.TenantSettingsService;
import com.adwiiseagent.service.cache.TenantSettingsCacheInvalidation;
import com.adwiiseagent.service.dto.TenantSettingsDTO;
import com.adwiiseagent.service.mapper.TenantSettingsMapper;
import com.adwiiseagent.service.validation.TenantSettingsAdsenseFieldsValidator;
import com.adwiiseagent.service.validation.TenantSettingsHeroFieldsValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.TenantSettings}.
 */
@Service
@Transactional
public class TenantSettingsServiceImpl implements TenantSettingsService {

    private static final Logger LOG = LoggerFactory.getLogger(TenantSettingsServiceImpl.class);

    private final TenantSettingsRepository tenantSettingsRepository;

    private final TenantSettingsMapper tenantSettingsMapper;

    private final S3Service s3Service;

    private final TenantSettingsCacheInvalidation tenantSettingsCacheInvalidation;

    private final TenantOrganizationRepository tenantOrganizationRepository;

    private final EntityManager entityManager;

    public TenantSettingsServiceImpl(
        TenantSettingsRepository tenantSettingsRepository,
        TenantSettingsMapper tenantSettingsMapper,
        S3Service s3Service,
        TenantSettingsCacheInvalidation tenantSettingsCacheInvalidation,
        TenantOrganizationRepository tenantOrganizationRepository,
        EntityManager entityManager
    ) {
        this.tenantSettingsRepository = tenantSettingsRepository;
        this.tenantSettingsMapper = tenantSettingsMapper;
        this.s3Service = s3Service;
        this.tenantSettingsCacheInvalidation = tenantSettingsCacheInvalidation;
        this.tenantOrganizationRepository = tenantOrganizationRepository;
        this.entityManager = entityManager;
    }

    @Override
    public TenantSettingsDTO save(TenantSettingsDTO tenantSettingsDTO) {
        LOG.debug("Request to save TenantSettings : {}", tenantSettingsDTO);
        TenantSettingsHeroFieldsValidator.applyCreateDefaultsAndValidate(tenantSettingsDTO);
        TenantSettingsAdsenseFieldsValidator.applyCreateDefaultsAndValidate(tenantSettingsDTO);
        TenantSettings tenantSettings = tenantSettingsMapper.toEntity(tenantSettingsDTO);
        requireOrganization(tenantSettings.getTenantOrganization());
        tenantSettings = tenantSettingsRepository.save(tenantSettings);
        tenantSettingsCacheInvalidation.evictForTenantSettingsId(tenantSettings.getId());
        return tenantSettingsMapper.toDto(tenantSettings);
    }

    @Override
    public TenantSettingsDTO update(TenantSettingsDTO tenantSettingsDTO) {
        LOG.debug("Request to update TenantSettings : {}", tenantSettingsDTO);
        TenantSettingsHeroFieldsValidator.validatePresentFields(tenantSettingsDTO);
        TenantSettingsAdsenseFieldsValidator.validatePresentFields(tenantSettingsDTO);
        // Use merge semantics like PATCH: clients often omit server-managed fields (e.g. homepageCacheVersion).
        // Full toEntity() would map null onto @NotNull fields and fail Bean Validation on commit.
        TenantSettings tenantSettings = tenantSettingsRepository
            .findById(tenantSettingsDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("TenantSettings not found with id " + tenantSettingsDTO.getId()));
        String previousTenantId = tenantSettings.getTenantId();
        validateTenantIdRename(previousTenantId, tenantSettingsDTO.getTenantId());
        tenantSettingsMapper.partialUpdate(tenantSettings, tenantSettingsDTO);
        requireOrganization(tenantSettings.getTenantOrganization());
        propagateTenantIdRename(previousTenantId, tenantSettings.getTenantId());
        normalizeRequiredFieldsBeforePersist(tenantSettings);
        tenantSettings = tenantSettingsRepository.save(tenantSettings);
        tenantSettingsCacheInvalidation.evictForTenantSettingsId(tenantSettings.getId());
        return tenantSettingsMapper.toDto(tenantSettings);
    }

    @Override
    public Optional<TenantSettingsDTO> partialUpdate(TenantSettingsDTO tenantSettingsDTO) {
        LOG.debug("Request to partially update TenantSettings : {}", tenantSettingsDTO);
        TenantSettingsHeroFieldsValidator.validatePresentFields(tenantSettingsDTO);
        TenantSettingsAdsenseFieldsValidator.validatePresentFields(tenantSettingsDTO);

        Optional<TenantSettingsDTO> result = tenantSettingsRepository
            .findById(tenantSettingsDTO.getId())
            .map(existingTenantSettings -> {
                String previousTenantId = existingTenantSettings.getTenantId();
                validateTenantIdRename(previousTenantId, tenantSettingsDTO.getTenantId());
                tenantSettingsMapper.partialUpdate(existingTenantSettings, tenantSettingsDTO);
                requireOrganization(existingTenantSettings.getTenantOrganization());
                propagateTenantIdRename(previousTenantId, existingTenantSettings.getTenantId());
                normalizeRequiredFieldsBeforePersist(existingTenantSettings);
                return existingTenantSettings;
            })
            .map(tenantSettingsRepository::save)
            .map(tenantSettingsMapper::toDto);
        result.ifPresent(dto -> tenantSettingsCacheInvalidation.evictForTenantSettingsId(dto.getId()));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
        value = "tenantSettings",
        key = "#id",
        // Optional return: Spring may expose the inner DTO as #result in unless — do not call isEmpty() on DTO
        unless = "#result == null || (#result instanceof T(java.util.Optional) && !#result.isPresent())"
    )
    public Optional<TenantSettingsDTO> findOne(Long id) {
        LOG.debug("Request to get TenantSettings : {}", id);
        return tenantSettingsRepository.findById(id).map(tenantSettingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TenantSettings : {}", id);
        tenantSettingsRepository.deleteById(id);
        tenantSettingsCacheInvalidation.evictForTenantSettingsId(id);
    }

    @Override
    public TenantSettingsDTO uploadEmailFooterHtml(String tenantId, MultipartFile file) {
        LOG.debug("Request to upload email footer HTML for tenant: {}", tenantId);

        // 1. Validate tenant settings exists
        TenantSettings tenantSettings = tenantSettingsRepository
            .findByTenantId(tenantId)
            .orElseThrow(() -> new EntityNotFoundException("Tenant settings not found for tenant: " + tenantId));

        // 2. Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        // Validate file type (HTML files)
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("text/html") && !file.getOriginalFilename().endsWith(".html"))) {
            throw new IllegalArgumentException("File must be an HTML file");
        }

        // Validate file size (max 1MB for HTML)
        if (file.getSize() > 1024 * 1024) {
            throw new IllegalArgumentException("File size must be less than 1MB");
        }

        // 3. Generate S3 path
        String s3Path = s3Service.generateTenantEmailFooterHtmlPath(tenantId);

        // 4. Upload to S3
        String s3Url = s3Service.uploadFile(s3Path, file);
        LOG.debug("Uploaded email footer HTML to S3: {}", s3Url);

        // 5. Update tenant_settings table with email footer HTML URL
        try {
            TenantSettingsDTO settingsDTO = tenantSettingsMapper.toDto(tenantSettings);
            settingsDTO.setEmailFooterHtmlUrl(s3Url);
            settingsDTO.setUpdatedAt(ZonedDateTime.now());
            return partialUpdate(settingsDTO)
                .orElseThrow(() -> new RuntimeException("Failed to update tenant settings with email footer HTML URL"));
        } catch (Exception e) {
            LOG.error("Failed to update TenantSettingsDTO with email footer HTML URL for tenant {}: {}", tenantId, e.getMessage(), e);
            // Fallback to direct entity update if service update fails
            try {
                tenantSettings.setEmailFooterHtmlUrl(s3Url);
                tenantSettings.setUpdatedAt(ZonedDateTime.now());
                TenantSettings saved = tenantSettingsRepository.save(tenantSettings);
                tenantSettingsCacheInvalidation.evictForTenantSettingsId(saved.getId());
                LOG.warn("Updated tenant settings {} with email footer HTML URL via direct entity update (fallback)", tenantId);
                return tenantSettingsMapper.toDto(saved);
            } catch (Exception fallbackException) {
                LOG.error(
                    "Failed to update TenantSettings with email footer HTML URL via fallback for tenant {}: {}",
                    tenantId,
                    fallbackException.getMessage(),
                    fallbackException
                );
                throw new RuntimeException("Failed to update tenant settings with email footer HTML URL", fallbackException);
            }
        }
    }

    @Override
    public TenantSettingsDTO uploadTenantLogo(String tenantId, MultipartFile file) {
        LOG.debug("Request to upload tenant logo for tenant: {}", tenantId);

        // 1. Validate tenant settings exists
        TenantSettings tenantSettings = tenantSettingsRepository
            .findByTenantId(tenantId)
            .orElseThrow(() -> new EntityNotFoundException("Tenant settings not found for tenant: " + tenantId));

        // 2. Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        // Validate file type (images only)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        // Validate file size (max 5MB for logo)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File size must be less than 5MB");
        }

        // 3. Generate S3 path
        String s3Path = s3Service.generateTenantLogoImagePath(tenantId, file.getOriginalFilename());

        // 4. Upload to S3
        String s3Url = s3Service.uploadFile(s3Path, file);
        LOG.debug("Uploaded tenant logo to S3: {}", s3Url);

        // 5. Update tenant_settings table with logo image URL
        try {
            TenantSettingsDTO settingsDTO = tenantSettingsMapper.toDto(tenantSettings);
            settingsDTO.setLogoImageUrl(s3Url);
            settingsDTO.setUpdatedAt(ZonedDateTime.now());
            return partialUpdate(settingsDTO)
                .orElseThrow(() -> new RuntimeException("Failed to update tenant settings with logo image URL"));
        } catch (Exception e) {
            LOG.error("Failed to update TenantSettingsDTO with logo image URL for tenant {}: {}", tenantId, e.getMessage(), e);
            // Fallback to direct entity update if service update fails
            try {
                tenantSettings.setLogoImageUrl(s3Url);
                tenantSettings.setUpdatedAt(ZonedDateTime.now());
                TenantSettings saved = tenantSettingsRepository.save(tenantSettings);
                tenantSettingsCacheInvalidation.evictForTenantSettingsId(saved.getId());
                LOG.warn("Updated tenant settings {} with logo image URL via direct entity update (fallback)", tenantId);
                return tenantSettingsMapper.toDto(saved);
            } catch (Exception fallbackException) {
                LOG.error(
                    "Failed to update TenantSettings with logo image URL via fallback for tenant {}: {}",
                    tenantId,
                    fallbackException.getMessage(),
                    fallbackException
                );
                throw new RuntimeException("Failed to update tenant settings with logo image URL", fallbackException);
            }
        }
    }

    @Override
    public TenantSettingsDTO uploadEmailHeaderImage(String tenantId, MultipartFile file) {
        LOG.debug("Request to upload email header image for tenant: {}", tenantId);

        // 1. Validate tenant settings exists
        TenantSettings tenantSettings = tenantSettingsRepository
            .findByTenantId(tenantId)
            .orElseThrow(() -> new EntityNotFoundException("Tenant settings not found for tenant: " + tenantId));

        // 2. Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        // Validate file type (images only: PNG, JPG, JPEG, GIF, WEBP)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        // Validate specific image types
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

        // Validate file size (max 5MB for email header image)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File size must be less than 5MB");
        }

        // 3. Generate S3 path
        String s3Path = s3Service.generateTenantEmailHeaderImagePath(tenantId, file.getOriginalFilename());

        // 4. Upload to S3
        String s3Url = s3Service.uploadFile(s3Path, file);
        LOG.debug("Uploaded email header image to S3: {}", s3Url);

        // 5. Update tenant_settings table with email header image URL
        try {
            TenantSettingsDTO settingsDTO = tenantSettingsMapper.toDto(tenantSettings);
            settingsDTO.setEmailHeaderImageUrl(s3Url);
            settingsDTO.setUpdatedAt(ZonedDateTime.now());
            return partialUpdate(settingsDTO)
                .orElseThrow(() -> new RuntimeException("Failed to update tenant settings with email header image URL"));
        } catch (Exception e) {
            LOG.error("Failed to update TenantSettingsDTO with email header image URL for tenant {}: {}", tenantId, e.getMessage(), e);
            // Fallback to direct entity update if service update fails
            try {
                tenantSettings.setEmailHeaderImageUrl(s3Url);
                tenantSettings.setUpdatedAt(ZonedDateTime.now());
                TenantSettings saved = tenantSettingsRepository.save(tenantSettings);
                tenantSettingsCacheInvalidation.evictForTenantSettingsId(saved.getId());
                LOG.warn("Updated tenant settings {} with email header image URL via direct entity update (fallback)", tenantId);
                return tenantSettingsMapper.toDto(saved);
            } catch (Exception fallbackException) {
                LOG.error(
                    "Failed to update TenantSettings with email header image URL via fallback for tenant {}: {}",
                    tenantId,
                    fallbackException.getMessage(),
                    fallbackException
                );
                throw new RuntimeException("Failed to update tenant settings with email header image URL", fallbackException);
            }
        }
    }

    @Override
    public String uploadDefaultHeroImage(String tenantId, MultipartFile file) {
        LOG.debug("Request to upload default hero image for tenant: {}", tenantId);

        tenantSettingsRepository
            .findByTenantId(tenantId)
            .orElseThrow(() -> new EntityNotFoundException("Tenant settings not found for tenant: " + tenantId));

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

        // Align with frontend MAX_FILE_BYTES (10 MB) in TenantDefaultHeroManager
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("File size must be less than 10MB");
        }

        String s3Path = s3Service.generateTenantDefaultHeroImagePath(tenantId, file.getOriginalFilename());
        String s3Url = s3Service.uploadFile(s3Path, file);
        LOG.debug("Uploaded default hero image to S3: {}", s3Url);
        return s3Url;
    }

    /**
     * Backfill {@code @NotNull} fields that may be null on entities loaded from rows created before
     * a column was added (DB default does not hydrate into the persistence context).
     */
    private void validateTenantIdRename(String previousTenantId, String requestedTenantId) {
        if (requestedTenantId == null || previousTenantId == null || previousTenantId.equals(requestedTenantId)) {
            return;
        }
        String newTenantId = requestedTenantId.trim();
        if (newTenantId.length() > 255 || !newTenantId.matches("[a-z0-9]+(?:_[a-z0-9]+)*")) {
            throw new BadRequestAlertException(
                "Tenant ID must use lowercase letters, numbers, and underscores",
                "tenantSettings",
                "tenantidinvalid"
            );
        }
        Number clash = (Number) entityManager
            .createNativeQuery("SELECT COUNT(*) FROM public.tenant_settings WHERE tenant_id = :newId")
            .setParameter("newId", newTenantId)
            .getSingleResult();
        if (clash.longValue() > 0) {
            throw new BadRequestAlertException("Tenant ID is already in use", "tenantSettings", "tenantidexists");
        }
    }

    private void requireOrganization(TenantOrganization organization) {
        if (organization == null || organization.getId() == null) {
            throw new BadRequestAlertException("An organization is required", "tenantSettings", "organizationrequired");
        }
        if (!tenantOrganizationRepository.existsById(organization.getId())) {
            throw new BadRequestAlertException("Organization was not found", "tenantSettings", "organizationnotfound");
        }
    }

    /**
     * Updates tables that store a website tenant id but do not foreign-key it to tenant_settings.
     * Tables that do foreign-key it follow the settings row through ON UPDATE CASCADE when this transaction flushes.
     */
    private void propagateTenantIdRename(String previousTenantId, String requestedTenantId) {
        if (previousTenantId == null || requestedTenantId == null || previousTenantId.equals(requestedTenantId)) {
            return;
        }
        String newTenantId = requestedTenantId.trim();
        @SuppressWarnings("unchecked")
        List<Object> tables = entityManager
            .createNativeQuery(
                """
                SELECT c.table_name
                FROM information_schema.columns c
                JOIN information_schema.tables t
                  ON t.table_schema = c.table_schema
                 AND t.table_name = c.table_name
                WHERE c.table_schema = 'public'
                  AND c.column_name = 'tenant_id'
                  AND t.table_type = 'BASE TABLE'
                  AND c.table_name <> 'tenant_settings'
                  AND NOT EXISTS (
                    SELECT 1
                    FROM information_schema.table_constraints tc
                    JOIN information_schema.key_column_usage kcu
                      ON kcu.constraint_name = tc.constraint_name
                     AND kcu.table_schema = tc.table_schema
                    JOIN information_schema.constraint_column_usage ccu
                      ON ccu.constraint_name = tc.constraint_name
                     AND ccu.table_schema = tc.table_schema
                    WHERE tc.constraint_type = 'FOREIGN KEY'
                      AND tc.table_schema = 'public'
                      AND kcu.table_name = c.table_name
                      AND kcu.column_name = 'tenant_id'
                      AND ccu.table_name = 'tenant_settings'
                      AND ccu.column_name = 'tenant_id'
                  )
                """
            )
            .getResultList();
        for (Object table : tables) {
            String tableName = String.valueOf(table);
            if (!tableName.matches("[a-z0-9_]+")) {
                continue;
            }
            entityManager
                .createNativeQuery("UPDATE public." + tableName + " SET tenant_id = :newId WHERE tenant_id = :oldId")
                .setParameter("newId", newTenantId)
                .setParameter("oldId", previousTenantId)
                .executeUpdate();
        }
    }

    private static void normalizeRequiredFieldsBeforePersist(TenantSettings entity) {
        if (entity.getHomepageCacheVersion() == null) {
            entity.setHomepageCacheVersion(0L);
        }
        if (entity.getEnableGoogleAdsense() == null) {
            entity.setEnableGoogleAdsense(Boolean.FALSE);
        }
    }
}
