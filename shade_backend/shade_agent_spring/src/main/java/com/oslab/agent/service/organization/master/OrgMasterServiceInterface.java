package com.oslab.agent.service.organization.master;

import com.oslab.agent.model.entity.orgEntity.OrgInfo;
import com.oslab.agent.model.entity.orgEntity.OrgMembers;
import com.oslab.agent.model.transfer.orgDto.AddOrgReqDto;
import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;

import java.sql.SQLException;

public interface OrgMasterServiceInterface {
    Long registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;
    void deleteOrganizationFromPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;
    boolean addOrganizationToUser(OrgReqDto orgReqDto) throws SQLException;
    void deleteOrganizationFromUser(OrgReqDto orgReqDto) throws SQLException;

    boolean addOrganizationToContributor(AddOrgReqDto addOrgReqDto) throws SQLException;

    OrgInfo getOrgInfo(Integer user_id, Integer org_id) throws SQLException;

    OrgMembers getOrgMembers(Integer org_id) throws SQLException;

    Integer getOrgAdmin(Integer org_id) throws SQLException;

}
