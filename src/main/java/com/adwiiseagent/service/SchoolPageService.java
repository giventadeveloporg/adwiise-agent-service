package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolPageDTO;
import java.util.Optional;

public interface SchoolPageService {
    SchoolPageDTO save(SchoolPageDTO schoolPageDTO);

    SchoolPageDTO update(SchoolPageDTO schoolPageDTO);

    Optional<SchoolPageDTO> partialUpdate(SchoolPageDTO schoolPageDTO);

    Optional<SchoolPageDTO> findOne(Long id);

    void delete(Long id);
}
