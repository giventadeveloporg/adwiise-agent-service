package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolHighlightDTO;
import java.util.Optional;

public interface SchoolHighlightService {
    SchoolHighlightDTO save(SchoolHighlightDTO schoolHighlightDTO);

    SchoolHighlightDTO update(SchoolHighlightDTO schoolHighlightDTO);

    Optional<SchoolHighlightDTO> partialUpdate(SchoolHighlightDTO schoolHighlightDTO);

    Optional<SchoolHighlightDTO> findOne(Long id);

    void delete(Long id);
}
