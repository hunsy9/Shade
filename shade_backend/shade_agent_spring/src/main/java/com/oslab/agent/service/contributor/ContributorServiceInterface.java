package com.oslab.agent.service.contributor;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.model.transfer.contributorDto.EditPrivDto;
import com.oslab.agent.model.transfer.contributorDto.RegContributorDto;

import java.sql.SQLException;
import java.util.List;

public interface ContributorServiceInterface {
    boolean addContributor(ContributorDto contributorDto) throws SQLException;
    boolean registerContributor(RegContributorDto regContributorDto) throws SQLException;
    boolean deleteContributor(ContributorDto contributorDto) throws SQLException;
    boolean editContributorEmail(ContributorDto contributorDto) throws SQLException;
    boolean editPrivileges(List<EditPrivDto> editPrivDtos) throws SQLException;
}
