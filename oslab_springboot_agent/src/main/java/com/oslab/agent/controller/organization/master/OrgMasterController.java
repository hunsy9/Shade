package com.oslab.agent.controller.organization.master;

import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;
import com.oslab.agent.service.organization.master.OrgMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrgMasterController {
    private final OrgMasterService orgMasterService;

    @PostMapping("api/org/regOrganization")
    public ResponseEntity<?> registerOrganizationToPlatform(@RequestBody RegOrgReqDto regOrgReqDto) throws SQLException {
        boolean success = orgMasterService.registerOrganizationToPlatform(regOrgReqDto);
        return success ? ResponseEntity.ok().body("Success") : ResponseEntity.internalServerError().body("Fail");
    }

    @PostMapping("api/org/addOrganization")
    public ResponseEntity<?> addOrganizationToPlatform(@RequestBody OrgReqDto orgReqDto) throws SQLException {
        boolean success = orgMasterService.addOrganizationToUser(orgReqDto);
        return success ? ResponseEntity.ok().body("Success") : ResponseEntity.internalServerError().body("Fail");
    }

    @DeleteMapping("api/org/deleteOrganizationFromPlatform")
    public void deleteOrganizationFromPlatform(@RequestBody RegOrgReqDto regOrgReqDto) throws SQLException {
        orgMasterService.deleteOrganizationFromPlatform(regOrgReqDto);
    }

    @DeleteMapping("api/org/deleteOrganizationFromUser")
    public void deleteOrganizationFromUser(@RequestBody OrgReqDto orgReqDto) throws SQLException {
        orgMasterService.deleteOrganizationFromUser(orgReqDto);
    }

}
