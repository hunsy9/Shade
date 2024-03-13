package com.oslab.agent.repository.contributor;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.model.transfer.contributorDto.EditPrivDto;
import com.oslab.agent.model.transfer.contributorDto.RegContributorDto;
import com.oslab.agent.repository.contributor.mapper.ContributorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ContributorRepository {
    private final ContributorMapper contributorMapper;

    public boolean addContributor(ContributorDto contributorDto) throws SQLException {
        System.out.println(contributorDto.toString());
        return contributorMapper.addContributor(contributorDto);
    }
    public boolean registerContributor(RegContributorDto regContributorDto) throws SQLException{
        return contributorMapper.checkValidContributor(regContributorDto);
    }
    public boolean deleteContributor(ContributorDto contributorDto) throws SQLException {
        return contributorMapper.deleteContributor(contributorDto);
    }
    public boolean editContributorEmail(ContributorDto contributorDto) throws SQLException{
        return contributorMapper.editContributorEmail(contributorDto);
    }
    public boolean editPrivileges(List<EditPrivDto> editPrivDtos) throws SQLException{
        editPrivDtos.stream().forEach((editPrivDto) -> {
            try {
                contributorMapper.editPrivileges(editPrivDto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
