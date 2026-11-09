package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolActivityDTO;
import java.util.Optional;

public interface SchoolActivityService {
    SchoolActivityDTO save(SchoolActivityDTO schoolActivityDTO);

    SchoolActivityDTO update(SchoolActivityDTO schoolActivityDTO);

    Optional<SchoolActivityDTO> partialUpdate(SchoolActivityDTO schoolActivityDTO);

    Optional<SchoolActivityDTO> findOne(Long id);

    void delete(Long id);
}
