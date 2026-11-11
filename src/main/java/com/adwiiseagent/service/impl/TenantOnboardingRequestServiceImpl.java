package com.adwiiseagent.service.impl;

import static com.adwiiseagent.service.TenantOnboardingSupport.ENTITY_NAME;

import com.adwiiseagent.domain.TenantOnboardingRequest;
import com.adwiiseagent.domain.enumeration.OnboardingRequestSource;
import com.adwiiseagent.domain.enumeration.OnboardingRequestStatus;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TenantOnboardingRequestRepository;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.repository.TenantSettingsRepository;
import com.adwiiseagent.service.DomainOwnershipCatalogLookup;
import com.adwiiseagent.service.SatelliteDomainService;
import com.adwiiseagent.service.SiteTypeCatalogLookup;
import com.adwiiseagent.service.TenantOnboardingRequestService;
import com.adwiiseagent.service.TenantOnboardingSupport;
import com.adwiiseagent.service.dto.TenantOnboardingRejectDTO;
import com.adwiiseagent.service.dto.TenantOnboardingRequestDTO;
import com.adwiiseagent.service.dto.TenantOnboardingSubmitDTO;
import com.adwiiseagent.service.mapper.TenantOnboardingRequestMapper;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TenantOnboardingRequest}.
 */
@Service
@Transactional
public class TenantOnboardingRequestServiceImpl implements TenantOnboardingRequestService {

    private static final Logger LOG = LoggerFactory.getLogger(TenantOnboardingRequestServiceImpl.class);

    /** Crockford-style alphabet without 0/O/1/I/L so codes can be read over the phone. */
    private static final char[] CODE_ALPHABET = "23456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();

    private static final DateTimeFormatter CODE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final Set<OnboardingRequestStatus> EDITABLE = EnumSet.of(
        OnboardingRequestStatus.PENDING,
        OnboardingRequestStatus.FAILED
    );

    private final SecureRandom random = new SecureRandom();

    private final TenantOnboardingRequestRepository repository;

    private final TenantOnboardingRequestMapper mapper;

    private final SatelliteDomainService satelliteDomainService;

    private final SiteTypeCatalogLookup siteTypeCatalogLookup;

    private final DomainOwnershipCatalogLookup domainOwnershipCatalogLookup;

    private final TenantOrganizationRepository tenantOrganizationRepository;

    private final TenantSettingsRepository tenantSettingsRepository;

    public TenantOnboardingRequestServiceImpl(
        TenantOnboardingRequestRepository repository,
        TenantOnboardingRequestMapper mapper,
        SatelliteDomainService satelliteDomainService,
        SiteTypeCatalogLookup siteTypeCatalogLookup,
        DomainOwnershipCatalogLookup domainOwnershipCatalogLookup,
        TenantOrganizationRepository tenantOrganizationRepository,
        TenantSettingsRepository tenantSettingsRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.satelliteDomainService = satelliteDomainService;
        this.siteTypeCatalogLookup = siteTypeCatalogLookup;
        this.domainOwnershipCatalogLookup = domainOwnershipCatalogLookup;
        this.tenantOrganizationRepository = tenantOrganizationRepository;
        this.tenantSettingsRepository = tenantSettingsRepository;
    }

    @Override
    public TenantOnboardingRequestDTO submit(TenantOnboardingSubmitDTO in) {
        LOG.debug("Request to submit TenantOnboardingRequest for organization: {}", in.getOrganizationName());

        String hostname = TenantOnboardingSupport.normalizeHostname(in.getRequestedHostname());
        if (!TenantOnboardingSupport.isValidHostname(hostname)) {
            throw new BadRequestAlertException("Requested domain is not a valid hostname", ENTITY_NAME, "hostnameinvalid");
        }
        String email = TenantOnboardingSupport.normalizeEmail(in.getContactEmail());

        if (repository.existsByStatusAndRequestedHostnameIgnoreCase(OnboardingRequestStatus.PENDING, hostname)) {
            throw new BadRequestAlertException(
                "A pending onboarding request already exists for this domain",
                ENTITY_NAME,
                "hostnamepending"
            );
        }
        if (repository.existsByStatusAndContactEmailIgnoreCase(OnboardingRequestStatus.PENDING, email)) {
            throw new BadRequestAlertException(
                "A pending onboarding request already exists for this email address",
                ENTITY_NAME,
                "emailpending"
            );
        }
        if (isHostnameAlreadyOnPlatform(hostname)) {
            throw new BadRequestAlertException("This domain is already registered on the platform", ENTITY_NAME, "hostnametaken");
        }

        TenantOnboardingRequest entity = new TenantOnboardingRequest();
        entity.setRequestCode(nextRequestCode());
        entity.setStatus(OnboardingRequestStatus.PENDING);
        entity.setSource(in.getSource() != null ? in.getSource() : OnboardingRequestSource.PUBLIC_FORM);
        entity.setOrganizationName(in.getOrganizationName().trim());
        String siteType = in.getSiteType() != null && !in.getSiteType().isBlank() ? in.getSiteType().trim() : "EVENT_ORG";
        siteTypeCatalogLookup.requireAssignable(siteType, null);
        entity.setSiteType(siteType);
        entity.setDescription(TenantOnboardingSupport.trimToNull(in.getDescription()));
        entity.setContactFirstName(TenantOnboardingSupport.trimToNull(in.getContactFirstName()));
        entity.setContactLastName(TenantOnboardingSupport.trimToNull(in.getContactLastName()));
        entity.setContactEmail(email);
        entity.setContactPhone(TenantOnboardingSupport.trimToNull(in.getContactPhone()));
        entity.setAddressLine1(TenantOnboardingSupport.trimToNull(in.getAddressLine1()));
        entity.setAddressLine2(TenantOnboardingSupport.trimToNull(in.getAddressLine2()));
        entity.setCity(TenantOnboardingSupport.trimToNull(in.getCity()));
        entity.setStateProvince(TenantOnboardingSupport.trimToNull(in.getStateProvince()));
        entity.setZipCode(TenantOnboardingSupport.trimToNull(in.getZipCode()));
        String country = TenantOnboardingSupport.trimToNull(in.getCountry());
        entity.setCountry(country != null ? country : "US");
        entity.setRequestedHostname(hostname);
        entity.setDomainOwnership(domainOwnershipCatalogLookup.requireAssignable(in.getDomainOwnership()));
        entity.setRegistrarNotes(TenantOnboardingSupport.trimToNull(in.getRegistrarNotes()));
        entity.setSourceOrganizationId(in.getSourceOrganizationId());
        entity.setPrimaryColor(TenantOnboardingSupport.trimToNull(in.getPrimaryColor()));
        entity.setSecondaryColor(TenantOnboardingSupport.trimToNull(in.getSecondaryColor()));
        entity.setLogoUrl(TenantOnboardingSupport.trimToNull(in.getLogoUrl()));
        entity.setWantsPayments(Boolean.TRUE.equals(in.getWantsPayments()));
        entity.setCustomerNotes(TenantOnboardingSupport.trimToNull(in.getCustomerNotes()));
        entity.setSubmitterIp(TenantOnboardingSupport.trimToNull(in.getSubmitterIp()));
        entity.setUserAgent(truncate(TenantOnboardingSupport.trimToNull(in.getUserAgent()), 512));

        entity = repository.save(entity);
        LOG.info("[ONBOARDING] Submitted request {} (id={}) for host {}", entity.getRequestCode(), entity.getId(), hostname);
        return mapper.toDto(entity);
    }

    @Override
    public Optional<TenantOnboardingRequestDTO> partialUpdate(TenantOnboardingRequestDTO dto) {
        LOG.debug("Request to partially update TenantOnboardingRequest : {}", dto);
        return repository
            .findById(dto.getId())
            .map(existing -> {
                if (!EDITABLE.contains(existing.getStatus())) {
                    throw new BadRequestAlertException(
                        "Only PENDING or FAILED requests can be edited (current: " + existing.getStatus() + ")",
                        ENTITY_NAME,
                        "notEditable"
                    );
                }
                if (dto.getRequestedHostname() != null) {
                    String hostname = TenantOnboardingSupport.normalizeHostname(dto.getRequestedHostname());
                    if (!TenantOnboardingSupport.isValidHostname(hostname)) {
                        throw new BadRequestAlertException("Requested domain is not a valid hostname", ENTITY_NAME, "hostnameinvalid");
                    }
                    dto.setRequestedHostname(hostname);
                }
                if (dto.getContactEmail() != null) {
                    dto.setContactEmail(TenantOnboardingSupport.normalizeEmail(dto.getContactEmail()));
                }
                if (dto.getNoreplyEmail() != null) {
                    dto.setNoreplyEmail(TenantOnboardingSupport.normalizeEmail(dto.getNoreplyEmail()));
                }
                if (dto.getAssignedTenantId() != null) {
                    String tenantId = TenantOnboardingSupport.trimToNull(dto.getAssignedTenantId());
                    dto.setAssignedTenantId(tenantId);
                    if (tenantId != null && repository.existsByAssignedTenantIdAndIdNot(tenantId, existing.getId())) {
                        boolean live = tenantSettingsRepository.findByTenantId(tenantId).isPresent();
                        if (live) {
                            throw new BadRequestAlertException(
                                "Another onboarding request already uses this tenant ID",
                                ENTITY_NAME,
                                "tenantidtaken"
                            );
                        }
                        repository.releaseAssignedTenantIdExcept(tenantId, existing.getId());
                    }
                }
                if (dto.getSiteType() != null) {
                    String siteType = dto.getSiteType().trim();
                    siteTypeCatalogLookup.requireAssignable(siteType, null);
                    dto.setSiteType(siteType);
                }
                if (dto.getDomainOwnership() != null) {
                    dto.setDomainOwnership(domainOwnershipCatalogLookup.requireAssignable(dto.getDomainOwnership()));
                }
                mapper.partialUpdate(existing, dto);
                if (dto.getRegistrarNotes() != null) {
                    existing.setRegistrarNotes(TenantOnboardingSupport.trimToNull(dto.getRegistrarNotes()));
                }
                return existing;
            })
            .map(repository::save)
            .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantOnboardingRequestDTO> findOne(Long id) {
        LOG.debug("Request to get TenantOnboardingRequest : {}", id);
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public TenantOnboardingRequestDTO reject(Long id, TenantOnboardingRejectDTO rejectDTO) {
        LOG.debug("Request to reject TenantOnboardingRequest : {}", id);
        TenantOnboardingRequest entity = repository
            .findByIdForUpdate(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
        if (!EDITABLE.contains(entity.getStatus())) {
            throw new BadRequestAlertException(
                "Only PENDING or FAILED requests can be rejected (current: " + entity.getStatus() + ")",
                ENTITY_NAME,
                "invalidTransition"
            );
        }
        ZonedDateTime now = ZonedDateTime.now();
        entity.setStatus(OnboardingRequestStatus.REJECTED);
        entity.setRejectedAt(now);
        entity.setReviewedAt(now);
        entity.setAdminComments(rejectDTO.getAdminComments().trim());
        entity.setReviewedByClerkUserId(TenantOnboardingSupport.trimToNull(rejectDTO.getReviewedByClerkUserId()));
        entity.setReviewedByEmail(TenantOnboardingSupport.trimToNull(rejectDTO.getReviewedByEmail()));
        entity = repository.save(entity);
        LOG.info("[ONBOARDING] Rejected request {} (id={})", entity.getRequestCode(), entity.getId());
        return mapper.toDto(entity);
    }

    private boolean isHostnameAlreadyOnPlatform(String hostname) {
        return satelliteDomainService.hostnameHeldByLiveOrganization(hostname);
    }

    private String nextRequestCode() {
        String datePart = LocalDate.now(ZoneOffset.UTC).format(CODE_DATE);
        for (int attempt = 0; attempt < 10; attempt++) {
            StringBuilder sb = new StringBuilder("ONB-").append(datePart).append('-');
            for (int i = 0; i < 5; i++) {
                sb.append(CODE_ALPHABET[random.nextInt(CODE_ALPHABET.length)]);
            }
            String code = sb.toString();
            if (!repository.existsByRequestCode(code)) {
                return code;
            }
        }
        throw new IllegalStateException("Could not allocate a unique onboarding request code");
    }

    private static String truncate(String value, int max) {
        return value == null || value.length() <= max ? value : value.substring(0, max);
    }
}
