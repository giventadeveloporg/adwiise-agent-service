package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolStaffMember;
import com.adwiiseagent.repository.SchoolStaffMemberRepository;
import com.adwiiseagent.service.SchoolStaffMemberService;
import com.adwiiseagent.service.dto.SchoolStaffMemberDTO;
import com.adwiiseagent.service.mapper.SchoolStaffMemberMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolStaffMemberServiceImpl implements SchoolStaffMemberService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolStaffMemberServiceImpl.class);

    private final SchoolStaffMemberRepository schoolStaffMemberRepository;

    private final SchoolStaffMemberMapper schoolStaffMemberMapper;

    public SchoolStaffMemberServiceImpl(
        SchoolStaffMemberRepository schoolStaffMemberRepository,
        SchoolStaffMemberMapper schoolStaffMemberMapper
    ) {
        this.schoolStaffMemberRepository = schoolStaffMemberRepository;
        this.schoolStaffMemberMapper = schoolStaffMemberMapper;
    }

    @Override
    public SchoolStaffMemberDTO save(SchoolStaffMemberDTO schoolStaffMemberDTO) {
        LOG.debug("Request to save SchoolStaffMember : {}", schoolStaffMemberDTO);
        SchoolStaffMember schoolStaffMember = schoolStaffMemberMapper.toEntity(schoolStaffMemberDTO);
        if (schoolStaffMember.getId() != null) {
            LOG.warn(
                "SchoolStaffMember has ID {} set during create operation. Clearing ID to force sequence generation.",
                schoolStaffMember.getId()
            );
            schoolStaffMember.setId(null);
        }
        schoolStaffMember = schoolStaffMemberRepository.save(schoolStaffMember);
        return schoolStaffMemberMapper.toDto(schoolStaffMember);
    }

    @Override
    public SchoolStaffMemberDTO update(SchoolStaffMemberDTO schoolStaffMemberDTO) {
        LOG.debug("Request to update SchoolStaffMember : {}", schoolStaffMemberDTO);
        SchoolStaffMember schoolStaffMember = schoolStaffMemberMapper.toEntity(schoolStaffMemberDTO);
        schoolStaffMember = schoolStaffMemberRepository.save(schoolStaffMember);
        return schoolStaffMemberMapper.toDto(schoolStaffMember);
    }

    @Override
    public Optional<SchoolStaffMemberDTO> partialUpdate(SchoolStaffMemberDTO schoolStaffMemberDTO) {
        LOG.debug("Request to partially update SchoolStaffMember : {}", schoolStaffMemberDTO);
        return schoolStaffMemberRepository
            .findById(schoolStaffMemberDTO.getId())
            .map(existing -> {
                schoolStaffMemberMapper.partialUpdate(existing, schoolStaffMemberDTO);
                return existing;
            })
            .map(schoolStaffMemberRepository::save)
            .map(schoolStaffMemberMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolStaffMemberDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolStaffMember : {}", id);
        return schoolStaffMemberRepository.findById(id).map(schoolStaffMemberMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolStaffMember : {}", id);
        schoolStaffMemberRepository.deleteById(id);
    }
}
