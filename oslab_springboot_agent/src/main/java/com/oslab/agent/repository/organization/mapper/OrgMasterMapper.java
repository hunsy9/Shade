package com.oslab.agent.repository.organization.mapper;

import com.oslab.agent.model.entity.orgEntity.OrgContributor;
import com.oslab.agent.model.transfer.orgDto.AddOrgReqDto;
import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.entity.orgEntity.ProjectInfo;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface OrgMasterMapper {
    Long registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;

    void deleteOrganizationFromPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;

    boolean addOrganizationToUser(OrgReqDto orgReqDto) throws SQLException;

    void deleteOrganizationFromUser(OrgReqDto orgReqDto) throws SQLException;

    boolean addOrganizationToContributor(AddOrgReqDto addOrgReqDto) throws SQLException;

    List<ProjectInfo> getProjectInfo(Integer org_id) throws SQLException;

    List<Integer> getOrgUserPrivileges(Integer user_id) throws SQLException;

    List<OrgContributor> getOrgContributors(Integer org_id) throws SQLException;
}
