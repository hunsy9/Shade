package com.oslab.agent.repository.contributor.mapper;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.model.transfer.contributorDto.EditPrivDto;
import com.oslab.agent.model.transfer.contributorDto.RegContributorDto;
import com.oslab.agent.model.entity.orgEntity.OrgMembers;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.stream.Stream;

@Mapper
public interface ContributorMapper {
    boolean addContributor(ContributorDto contributorDto) throws SQLException;
    boolean checkValidContributor(RegContributorDto regContributorDto) throws SQLException;
    boolean deleteContributor(ContributorDto contributorDto) throws SQLException;
    boolean editContributorEmail(ContributorDto contributorDto) throws SQLException;
    Stream<Boolean> editPrivileges(EditPrivDto editPrivDto) throws SQLException;

    OrgMembers getOrgMembers() throws SQLException;
}
