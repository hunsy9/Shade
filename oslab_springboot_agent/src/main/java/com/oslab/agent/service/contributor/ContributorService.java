package com.oslab.agent.service.contributor;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.repository.contributor.ContributorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContributorService implements ContributorServiceInterface{

    private final ContributorRepository contributorRepository;
    public boolean addContributor(ContributorDto contributorDto) {
        return false;
    }
    public boolean registerContributor(ContributorDto contributorDto){return false;}
    public boolean deleteContributor(ContributorDto contributorDto) {
        return false;
    }
    public boolean editContributorEmail(ContributorDto contributorDto) {
        return false;
    }
    public boolean editPrivileges(){return false;}
}
