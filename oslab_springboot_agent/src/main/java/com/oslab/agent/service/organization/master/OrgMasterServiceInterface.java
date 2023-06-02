package com.oslab.agent.service.organization.master;

import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;

import java.sql.SQLException;

public interface OrgMasterServiceInterface {
    public boolean registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;
    public void deleteOrganizationFromPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;
    public boolean addOrganizationToUser(OrgReqDto orgReqDto) throws SQLException;
    public void deleteOrganizationFromUser(OrgReqDto orgReqDto) throws SQLException;

}
