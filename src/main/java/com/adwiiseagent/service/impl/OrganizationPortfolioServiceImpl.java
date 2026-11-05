package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.OrganizationPortfolio;
import com.adwiiseagent.domain.OrganizationPortfolioMember;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.OrganizationPortfolioMemberRepository;
import com.adwiiseagent.repository.OrganizationPortfolioRepository;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.service.OrganizationPortfolioService;
import com.adwiiseagent.service.dto.OrganizationPortfolioDTO;
import com.adwiiseagent.service.dto.OrganizationPortfolioMemberDTO;
import com.adwiiseagent.service.mapper.OrganizationPortfolioMapper;
import com.adwiiseagent.service.mapper.OrganizationPortfolioMemberMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationPortfolioServiceImpl implements OrganizationPortfolioService {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationPortfolioServiceImpl.class);
    private static final String ENTITY_NAME = "organizationPortfolio";

    private final OrganizationPortfolioRepository portfolioRepository;
    private final OrganizationPortfolioMemberRepository memberRepository;
    private final TenantOrganizationRepository tenantOrganizationRepository;
    private final OrganizationPortfolioMapper portfolioMapper;
    private final OrganizationPortfolioMemberMapper memberMapper;

    public OrganizationPortfolioServiceImpl(
        OrganizationPortfolioRepository portfolioRepository,
        OrganizationPortfolioMemberRepository memberRepository,
        TenantOrganizationRepository tenantOrganizationRepository,
        OrganizationPortfolioMapper portfolioMapper,
        OrganizationPortfolioMemberMapper memberMapper
    ) {
        this.portfolioRepository = portfolioRepository;
        this.memberRepository = memberRepository;
        this.tenantOrganizationRepository = tenantOrganizationRepository;
        this.portfolioMapper = portfolioMapper;
        this.memberMapper = memberMapper;
    }

    @Override
    public OrganizationPortfolioDTO save(OrganizationPortfolioDTO dto) {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new portfolio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String name = requireName(dto.getPortfolioName());
        String primaryTenantId = requireExistingTenant(dto.getPrimaryTenantId());
        if (memberRepository.existsByTenantId(primaryTenantId) || portfolioRepository.existsByPrimaryTenantId(primaryTenantId)) {
            throw new BadRequestAlertException("Tenant already belongs to a portfolio: " + primaryTenantId, ENTITY_NAME, "tenanttaken");
        }

        OrganizationPortfolio portfolio = new OrganizationPortfolio();
        portfolio.setPortfolioName(name);
        portfolio.setDescription(blankToNull(dto.getDescription()));
        portfolio.setPrimaryTenantId(primaryTenantId);
        portfolio.setIsActive(dto.getIsActive() == null || dto.getIsActive());
        portfolio = portfolioRepository.save(portfolio);

        OrganizationPortfolioMember primary = new OrganizationPortfolioMember();
        primary.setPortfolioId(portfolio.getId());
        primary.setTenantId(primaryTenantId);
        primary.setIsPrimary(true);
        memberRepository.save(primary);

        LOG.debug("Created organization portfolio {} for primary tenant {}", portfolio.getId(), primaryTenantId);
        return portfolioMapper.toDto(portfolio);
    }

    @Override
    public OrganizationPortfolioDTO update(OrganizationPortfolioDTO dto) {
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganizationPortfolio portfolio = load(dto.getId());
        portfolio.setPortfolioName(requireName(dto.getPortfolioName()));
        portfolio.setDescription(blankToNull(dto.getDescription()));
        if (dto.getIsActive() != null) {
            portfolio.setIsActive(dto.getIsActive());
        }
        String nextPrimary = requireExistingTenant(dto.getPrimaryTenantId());
        if (!nextPrimary.equals(portfolio.getPrimaryTenantId())) {
            changePrimary(portfolio, nextPrimary);
        }
        return portfolioMapper.toDto(portfolioRepository.save(portfolio));
    }

    @Override
    public Optional<OrganizationPortfolioDTO> partialUpdate(OrganizationPortfolioDTO dto) {
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return portfolioRepository
            .findById(dto.getId())
            .map(existing -> {
                if (dto.getPortfolioName() != null) {
                    existing.setPortfolioName(requireName(dto.getPortfolioName()));
                }
                if (dto.getDescription() != null) {
                    existing.setDescription(blankToNull(dto.getDescription()));
                }
                if (dto.getIsActive() != null) {
                    existing.setIsActive(dto.getIsActive());
                }
                if (dto.getPrimaryTenantId() != null && !dto.getPrimaryTenantId().isBlank()) {
                    String nextPrimary = requireExistingTenant(dto.getPrimaryTenantId());
                    if (!nextPrimary.equals(existing.getPrimaryTenantId())) {
                        changePrimary(existing, nextPrimary);
                    }
                }
                return portfolioMapper.toDto(portfolioRepository.save(existing));
            });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationPortfolioDTO> findOne(Long id) {
        return portfolioRepository.findById(id).map(portfolioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if (!portfolioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        memberRepository.deleteByPortfolioId(id);
        portfolioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public void requireActivePortfolio(Long id) {
        OrganizationPortfolio portfolio = portfolioRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Portfolio not found", ENTITY_NAME, "idnotfound"));
        if (!Boolean.TRUE.equals(portfolio.getIsActive())) {
            throw new BadRequestAlertException("Portfolio is inactive", ENTITY_NAME, "inactive");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationPortfolioMemberDTO> findMembers(Long portfolioId) {
        load(portfolioId);
        return memberMapper.toDto(memberRepository.findByPortfolioIdOrderByIsPrimaryDescTenantIdAsc(portfolioId));
    }

    @Override
    public OrganizationPortfolioMemberDTO addMember(Long portfolioId, String tenantId, boolean asPrimary) {
        OrganizationPortfolio portfolio = load(portfolioId);
        String normalized = requireExistingTenant(tenantId);
        Optional<OrganizationPortfolioMember> existing = memberRepository.findByTenantId(normalized);
        if (existing.isPresent()) {
            if (existing.orElseThrow().getPortfolioId().equals(portfolioId)) {
                throw new BadRequestAlertException("Tenant is already in this portfolio: " + normalized, ENTITY_NAME, "tenanttaken");
            }
            throw new BadRequestAlertException("Tenant already belongs to another portfolio: " + normalized, ENTITY_NAME, "tenanttaken");
        }
        if (asPrimary) {
            changePrimary(portfolio, normalized);
            portfolioRepository.save(portfolio);
            return memberRepository
                .findByPortfolioIdAndIsPrimaryTrue(portfolioId)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new BadRequestAlertException("Primary member was not saved", ENTITY_NAME, "primarymissing"));
        }
        OrganizationPortfolioMember member = new OrganizationPortfolioMember();
        member.setPortfolioId(portfolioId);
        member.setTenantId(normalized);
        member.setIsPrimary(false);
        return memberMapper.toDto(memberRepository.save(member));
    }

    @Override
    public void removeMember(Long portfolioId, Long memberId) {
        load(portfolioId);
        OrganizationPortfolioMember member = memberRepository
            .findById(memberId)
            .orElseThrow(() -> new BadRequestAlertException("Member not found", ENTITY_NAME, "membernotfound"));
        if (!portfolioId.equals(member.getPortfolioId())) {
            throw new BadRequestAlertException("Member not found", ENTITY_NAME, "membernotfound");
        }
        if (Boolean.TRUE.equals(member.getIsPrimary())) {
            throw new BadRequestAlertException("Choose another primary tenant before removing this one", ENTITY_NAME, "primaryrequired");
        }
        memberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findMemberTenantIdsForPrimaries(Collection<String> primaryTenantIds) {
        if (primaryTenantIds == null || primaryTenantIds.isEmpty()) {
            return List.of();
        }
        Set<String> wanted = new LinkedHashSet<>();
        for (String raw : primaryTenantIds) {
            if (raw != null && !raw.isBlank()) {
                wanted.add(raw.trim());
            }
        }
        if (wanted.isEmpty()) {
            return List.of();
        }
        List<OrganizationPortfolio> portfolios = portfolioRepository.findByPrimaryTenantIdInAndIsActiveTrue(wanted);
        if (portfolios.isEmpty()) {
            return List.of();
        }
        List<Long> portfolioIds = portfolios.stream().map(OrganizationPortfolio::getId).toList();
        Set<String> tenantIds = new LinkedHashSet<>();
        for (OrganizationPortfolioMember member : memberRepository.findByPortfolioIdIn(portfolioIds)) {
            if (member.getTenantId() != null && !member.getTenantId().isBlank()) {
                tenantIds.add(member.getTenantId().trim());
            }
        }
        return new ArrayList<>(tenantIds);
    }

    private void changePrimary(OrganizationPortfolio portfolio, String nextPrimaryTenantId) {
        Optional<OrganizationPortfolioMember> existingMembership = memberRepository.findByTenantId(nextPrimaryTenantId);
        if (existingMembership.isPresent() && !existingMembership.orElseThrow().getPortfolioId().equals(portfolio.getId())) {
            throw new BadRequestAlertException(
                "Tenant already belongs to another portfolio: " + nextPrimaryTenantId,
                ENTITY_NAME,
                "tenanttaken"
            );
        }
        OrganizationPortfolioMember currentPrimary = memberRepository
            .findByPortfolioIdAndIsPrimaryTrue(portfolio.getId())
            .orElseThrow(() -> new BadRequestAlertException("Portfolio has no primary member", ENTITY_NAME, "primarymissing"));
        if (nextPrimaryTenantId.equals(currentPrimary.getTenantId())) {
            portfolio.setPrimaryTenantId(nextPrimaryTenantId);
            return;
        }
        currentPrimary.setIsPrimary(false);
        memberRepository.saveAndFlush(currentPrimary);

        OrganizationPortfolioMember nextPrimary = existingMembership.orElseGet(() -> {
            OrganizationPortfolioMember created = new OrganizationPortfolioMember();
            created.setPortfolioId(portfolio.getId());
            created.setTenantId(nextPrimaryTenantId);
            created.setIsPrimary(false);
            return memberRepository.saveAndFlush(created);
        });
        nextPrimary.setIsPrimary(true);
        memberRepository.saveAndFlush(nextPrimary);
        portfolio.setPrimaryTenantId(nextPrimaryTenantId);
    }

    private OrganizationPortfolio load(Long id) {
        return portfolioRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
    }

    private String requireExistingTenant(String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            throw new BadRequestAlertException("Tenant ID is required", ENTITY_NAME, "tenantrequired");
        }
        String normalized = tenantId.trim();
        if (!tenantOrganizationRepository.existsByTenantId(normalized)) {
            throw new BadRequestAlertException("Tenant does not exist: " + normalized, ENTITY_NAME, "tenantnotfound");
        }
        return normalized;
    }

    private String requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestAlertException("Portfolio name is required", ENTITY_NAME, "namerequired");
        }
        String trimmed = name.trim();
        if (trimmed.length() > 255) {
            throw new BadRequestAlertException("Portfolio name is too long", ENTITY_NAME, "nametoolong");
        }
        return trimmed;
    }

    private String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
