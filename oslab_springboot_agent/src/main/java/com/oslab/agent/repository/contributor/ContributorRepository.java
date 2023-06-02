package com.oslab.agent.repository.contributor;

import com.oslab.agent.repository.contributor.mapper.ContributorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ContributorRepository {
    private final ContributorMapper contributorMapper;

    public boolean addContributor() {
        return false;
    }
    public boolean registerContributor(){return false;}
    public boolean deleteContributor() {
        return false;
    }
    public boolean editContributorEmail() {
        return false;
    }
    public boolean editPrivileges(){return false;}
}
