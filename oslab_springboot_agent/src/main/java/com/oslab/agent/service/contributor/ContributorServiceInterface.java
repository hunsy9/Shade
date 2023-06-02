package com.oslab.agent.service.contributor;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;

public interface ContributorServiceInterface {
    boolean addContributor(ContributorDto contributorDto);
    boolean registerContributor(ContributorDto contributorDto);
    boolean deleteContributor(ContributorDto contributorDto);
    boolean editContributorEmail(ContributorDto contributorDto);
    boolean editPrivileges();
}
