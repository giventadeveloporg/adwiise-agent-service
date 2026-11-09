package com.adwiiseagent.service;

import com.adwiiseagent.domain.*;
import com.adwiiseagent.domain.SchoolFeeInvoice;
import com.adwiiseagent.repository.SchoolFeeInvoiceRepository;
import com.adwiiseagent.service.criteria.SchoolFeeInvoiceCriteria;
import com.adwiiseagent.service.dto.SchoolFeeInvoiceDTO;
import com.adwiiseagent.service.mapper.SchoolFeeInvoiceMapper;
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
public class SchoolFeeInvoiceQueryService extends QueryService<SchoolFeeInvoice> {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFeeInvoiceQueryService.class);

    private final SchoolFeeInvoiceRepository schoolFeeInvoiceRepository;

    private final SchoolFeeInvoiceMapper schoolFeeInvoiceMapper;

    public SchoolFeeInvoiceQueryService(
        SchoolFeeInvoiceRepository schoolFeeInvoiceRepository,
        SchoolFeeInvoiceMapper schoolFeeInvoiceMapper
    ) {
        this.schoolFeeInvoiceRepository = schoolFeeInvoiceRepository;
        this.schoolFeeInvoiceMapper = schoolFeeInvoiceMapper;
    }

    @Transactional(readOnly = true)
    public Page<SchoolFeeInvoiceDTO> findByCriteria(SchoolFeeInvoiceCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolFeeInvoice> specification = createSpecification(criteria);
        return schoolFeeInvoiceRepository.findAll(specification, page).map(schoolFeeInvoiceMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SchoolFeeInvoiceCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SchoolFeeInvoice> specification = createSpecification(criteria);
        return schoolFeeInvoiceRepository.count(specification);
    }

    protected Specification<SchoolFeeInvoice> createSpecification(SchoolFeeInvoiceCriteria criteria) {
        Specification<SchoolFeeInvoice> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), SchoolFeeInvoice_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), SchoolFeeInvoice_.tenantId) : null,
                    criteria.getTitle() != null ? buildStringSpecification(criteria.getTitle(), SchoolFeeInvoice_.title) : null,
                    criteria.getAcademicYear() != null
                        ? buildStringSpecification(criteria.getAcademicYear(), SchoolFeeInvoice_.academicYear)
                        : null,
                    criteria.getFeeKind() != null ? buildStringSpecification(criteria.getFeeKind(), SchoolFeeInvoice_.feeKind) : null,
                    criteria.getStudentName() != null
                        ? buildStringSpecification(criteria.getStudentName(), SchoolFeeInvoice_.studentName)
                        : null,
                    criteria.getClassLabel() != null
                        ? buildStringSpecification(criteria.getClassLabel(), SchoolFeeInvoice_.classLabel)
                        : null,
                    criteria.getPayerName() != null ? buildStringSpecification(criteria.getPayerName(), SchoolFeeInvoice_.payerName) : null,
                    criteria.getPayerEmail() != null
                        ? buildStringSpecification(criteria.getPayerEmail(), SchoolFeeInvoice_.payerEmail)
                        : null,
                    criteria.getPayerPhone() != null
                        ? buildStringSpecification(criteria.getPayerPhone(), SchoolFeeInvoice_.payerPhone)
                        : null,
                    criteria.getAmountDue() != null ? buildRangeSpecification(criteria.getAmountDue(), SchoolFeeInvoice_.amountDue) : null,
                    criteria.getCurrency() != null ? buildStringSpecification(criteria.getCurrency(), SchoolFeeInvoice_.currency) : null,
                    criteria.getDueDate() != null ? buildRangeSpecification(criteria.getDueDate(), SchoolFeeInvoice_.dueDate) : null,
                    criteria.getStatus() != null ? buildStringSpecification(criteria.getStatus(), SchoolFeeInvoice_.status) : null,
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null
                );
        }
        return specification;
    }
}
