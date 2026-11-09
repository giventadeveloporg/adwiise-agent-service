package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolStaffMemberDTO;
import java.util.Optional;

public interface SchoolStaffMemberService {
    SchoolStaffMemberDTO save(SchoolStaffMemberDTO schoolStaffMemberDTO);

    SchoolStaffMemberDTO update(SchoolStaffMemberDTO schoolStaffMemberDTO);

    Optional<SchoolStaffMemberDTO> partialUpdate(SchoolStaffMemberDTO schoolStaffMemberDTO);

    Optional<SchoolStaffMemberDTO> findOne(Long id);

    void delete(Long id);
}
