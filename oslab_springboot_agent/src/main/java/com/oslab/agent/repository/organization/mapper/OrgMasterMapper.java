package com.oslab.agent.repository.organization.mapper;

import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface OrgMasterMapper {
    boolean registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;

    void deleteOrganizationFromPlatform(RegOrgReqDto regOrgReqDto) throws SQLException;

    boolean addOrganizationToUser(OrgReqDto orgReqDto) throws SQLException;

    void deleteOrganizationFromUser(OrgReqDto orgReqDto) throws SQLException;
}
