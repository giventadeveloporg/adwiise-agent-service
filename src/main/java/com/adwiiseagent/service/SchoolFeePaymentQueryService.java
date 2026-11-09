package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolFeePayment;
import com.adwiiseagent.repository.SchoolFeePaymentRepository;
import com.adwiiseagent.service.criteria.SchoolFeePaymentCriteria;
import com.adwiiseagent.service.dto.SchoolFeePaymentDTO;
import com.adwiiseagent.service.mapper.SchoolFeePaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class SchoolFeePaymentQueryService extends QueryService<SchoolFeePayment> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFeePaymentQueryService.class);

    private final SchoolFeePaymentRepository schoolFeePaymentRepository;

    private final SchoolFeePaymentMapper schoolFeePaymentMapper;

    public SchoolFeePaymentQueryService(
        SchoolFeePaymentRepository schoolFeePaymentRepository,
        SchoolFeePaymentMapper schoolFeePaymentMapper
    ) {
        this.schoolFeePaymentRepository = schoolFeePaymentRepository;
        this.schoolFeePaymentMapper = schoolFeePaymentMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolFeePaymentDTO> findByCriteria(SchoolFeePaymentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolFeePayment> specification = createSpecification(criteria);
        return schoolFeePaymentRepository.findAll(specification, page).map(schoolFeePaymentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolFeePaymentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolFeePayment> specification = createSpecification(criteria);
        return schoolFeePaymentRepository.count(specification);
    }

    protected Specification<SchoolFeePayment> createSpecification(SchoolFeePaymentCriteria criteria) {
        Specification<SchoolFeePayment> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolFeePayment_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolFeePayment_.tenantId) : null,
                    criteria.getInvoiceId() != null ? buildRangeSpecification(criteria.getInvoiceId(), SchoolFeePayment_.invoiceId) : null,
                    criteria.getProviderName() != null
                        ? buildStringSpecification(criteria.getProviderName(), SchoolFeePayment_.providerName)
                        : null,
                    criteria.getProviderOrderId() != null
                        ? buildStringSpecification(criteria.getProviderOrderId(), SchoolFeePayment_.providerOrderId)
                        : null,
                    criteria.getProviderPaymentId() != null
                        ? buildStringSpecification(criteria.getProviderPaymentId(), SchoolFeePayment_.providerPaymentId)
                        : null,
                    criteria.getAmount() != null ? buildRangeSpecification(criteria.getAmount(), SchoolFeePayment_.amount) : null,
                    criteria.getCurrency() != null ? buildStringSpecification(criteria.getCurrency(), SchoolFeePayment_.currency) : null,
                    criteria.getStatus() != null ? buildStringSpecification(criteria.getStatus(), SchoolFeePayment_.status) : null,
                    criteria.getPaidAt() != null ? buildRangeSpecification(criteria.getPaidAt(), SchoolFeePayment_.paidAt) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
