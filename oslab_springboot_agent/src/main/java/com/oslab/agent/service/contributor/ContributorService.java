package com.oslab.agent.service.contributor;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.model.transfer.contributorDto.EditPrivDto;
import com.oslab.agent.model.transfer.contributorDto.RegContributorDto;
import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.repository.contributor.ContributorRepository;
import com.oslab.agent.repository.organization.master.OrgMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContributorService implements ContributorServiceInterface{

    private final ContributorRepository contributorRepository;
    private final OrgMasterRepository orgMasterRepository;
    public boolean addContributor(ContributorDto contributorDto) throws SQLException {
        return contributorRepository.addContributor(contributorDto);
    }
    public boolean registerContributor(RegContributorDto regContributorDto) throws SQLException{
        boolean valid = contributorRepository.registerContributor(regContributorDto);
        OrgReqDto orgReqDto = OrgReqDto.builder()
                .org_id(regContributorDto.getOrg_id())
                .user_id(regContributorDto.getUser_id())
                .build();
        return valid ? orgMasterRepository.addOrganizationToUser(orgReqDto) : false;
    }
    public boolean deleteContributor(ContributorDto contributorDto) throws SQLException{
        return contributorRepository.deleteContributor(contributorDto);
    }
    public boolean editContributorEmail(ContributorDto contributorDto) throws SQLException{
        return contributorRepository.editContributorEmail(contributorDto);
    }
    public boolean editPrivileges(List<EditPrivDto> editPrivDtos) throws SQLException {
        return contributorRepository.editPrivileges(editPrivDtos);
    }
}
