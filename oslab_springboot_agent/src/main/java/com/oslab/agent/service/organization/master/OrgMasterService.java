package com.oslab.agent.service.organization.master;

import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;
import com.oslab.agent.repository.organization.master.OrgMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrgMasterService implements OrgMasterServiceInterface {

    private final OrgMasterRepository orgMasterRepository;
    public boolean registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException {
        return orgMasterRepository.registerOrganizationToPlatform(regOrgReqDto);
    }

    public void deleteOrganizationFromPlatform(RegOrgReqDto regOrgReqDto) throws SQLException{
        orgMasterRepository.deleteOrganizationFromPlatform(regOrgReqDto);
    }

    public boolean addOrganizationToUser(OrgReqDto orgReqDto) throws SQLException{
        return orgMasterRepository.addOrganizationToUser(orgReqDto);
    }

    public void deleteOrganizationFromUser(OrgReqDto orgReqDto) throws SQLException{
        orgMasterRepository.deleteOrganizationFromUser(orgReqDto);
    }

}
