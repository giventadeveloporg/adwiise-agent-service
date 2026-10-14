package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.ProfileAffiliationDTO;
import java.util.Optional;

public interface ProfileAffiliationService {
    ProfileAffiliationDTO save(ProfileAffiliationDTO profileAffiliationDTO);
    ProfileAffiliationDTO update(ProfileAffiliationDTO profileAffiliationDTO);
    Optional<ProfileAffiliationDTO> partialUpdate(ProfileAffiliationDTO profileAffiliationDTO);
    Optional<ProfileAffiliationDTO> findOne(Long id);
    void delete(Long id);
}
