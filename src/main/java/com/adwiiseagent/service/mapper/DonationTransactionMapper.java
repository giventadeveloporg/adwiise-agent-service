package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.DonationTransaction;
import com.adwiiseagent.service.dto.DonationTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonationTransaction} and its DTO {@link DonationTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonationTransactionMapper extends EntityMapper<DonationTransactionDTO, DonationTransaction> {
    DonationTransactionDTO toDto(DonationTransaction s);
}
