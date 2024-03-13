package com.oslab.agent.service.organization.master;

import com.oslab.agent.model.entity.orgEntity.OrgInfo;
import com.oslab.agent.model.entity.orgEntity.OrgMembers;
import com.oslab.agent.model.transfer.orgDto.AddOrgReqDto;
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
    public Long registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException {
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

    public boolean addOrganizationToContributor(AddOrgReqDto addOrgReqDto) throws SQLException{
        return orgMasterRepository.addOrganizationToContributor(addOrgReqDto);
    }

    public OrgInfo getOrgInfo(Integer user_id, Integer org_id) throws SQLException{
        return orgMasterRepository.getOrgInfo(user_id, org_id);
    }

    public OrgMembers getOrgMembers(Integer org_id) throws SQLException{
        return orgMasterRepository.getOrgMembers(org_id);
    }

    public Integer getOrgAdmin(Integer org_id) throws SQLException{
        return orgMasterRepository.getOrgAdmin(org_id);
    }
}
