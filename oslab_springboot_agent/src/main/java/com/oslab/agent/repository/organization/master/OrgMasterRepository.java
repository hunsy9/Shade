package com.oslab.agent.repository.organization.master;

import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;
import com.oslab.agent.repository.organization.mapper.OrgMasterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrgMasterRepository {

    private final OrgMasterMapper orgMasterMapper;

    public boolean registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException {
        return orgMasterMapper.registerOrganizationToPlatform(regOrgReqDto);
    }

    public void deleteOrganizationFromPlatform(RegOrgReqDto regOrgReqDto) throws SQLException{
        orgMasterMapper.deleteOrganizationFromPlatform(regOrgReqDto);
    }

    public boolean addOrganizationToUser(OrgReqDto orgReqDto) throws SQLException{
        return orgMasterMapper.addOrganizationToUser(orgReqDto);
    }

    public void deleteOrganizationFromUser(OrgReqDto orgReqDto) throws SQLException{
        orgMasterMapper.deleteOrganizationFromUser(orgReqDto);
    }

}
