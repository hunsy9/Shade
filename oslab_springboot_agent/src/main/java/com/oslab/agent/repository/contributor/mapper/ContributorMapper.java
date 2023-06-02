package com.oslab.agent.repository.contributor.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContributorMapper {
    boolean addContributor();
    boolean registerContributor();
    boolean deleteContributor();
    boolean editContributorEmail();
    boolean editPrivileges();
}
