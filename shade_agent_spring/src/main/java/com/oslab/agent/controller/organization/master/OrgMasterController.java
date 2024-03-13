package com.oslab.agent.controller.organization.master;

import com.oslab.agent.model.entity.orgEntity.OrgContributor;
import com.oslab.agent.model.entity.orgEntity.OrgInfo;
import com.oslab.agent.model.entity.orgEntity.OrgMembers;
import com.oslab.agent.model.transfer.orgDto.AddOrgReqDto;
import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;
import com.oslab.agent.service.organization.master.OrgMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@Slf4j
@CrossOrigin(origins="*")
@RequiredArgsConstructor
public class OrgMasterController {
    private final OrgMasterService orgMasterService;

    @PostMapping("api/org/regOrganization")
    public ResponseEntity<?> registerOrganizationToPlatform(@RequestBody RegOrgReqDto regOrgReqDto) throws SQLException {
        Long success = orgMasterService.registerOrganizationToPlatform(regOrgReqDto);
        return ResponseEntity.ok().body(success);
    }

    @PostMapping("api/org/addOrganization")
    public ResponseEntity<?> addOrganizationToPlatform(@RequestBody OrgReqDto orgReqDto) throws SQLException {
        boolean success = orgMasterService.addOrganizationToUser(orgReqDto);
        return ResponseEntity.ok().body(success);
    }

    @PostMapping("api/org/addOrgContributor")
    public ResponseEntity<?> addOrganizationToContributor(@RequestBody AddOrgReqDto addOrgReqDto) throws SQLException {
        addOrgReqDto.setState("admin");
        boolean success = orgMasterService.addOrganizationToContributor(addOrgReqDto);
        return ResponseEntity.ok().body(success);
    }

    @DeleteMapping("api/org/deleteOrganizationFromPlatform")
    public void deleteOrganizationFromPlatform(@RequestBody RegOrgReqDto regOrgReqDto) throws SQLException {
        orgMasterService.deleteOrganizationFromPlatform(regOrgReqDto);
    }

    @DeleteMapping("api/org/deleteOrganizationFromUser")
    public void deleteOrganizationFromUser(@RequestBody OrgReqDto orgReqDto) throws SQLException {
        orgMasterService.deleteOrganizationFromUser(orgReqDto);
    }

    @GetMapping("api/org/getOrgInfo/{user_id}/{org_id}")
    public OrgInfo getOrgInfo(@PathVariable Integer user_id, @PathVariable Integer org_id) throws SQLException{
        return orgMasterService.getOrgInfo(user_id, org_id);
    }

    @GetMapping("api/org/getOrgMemberInfo/{org_id}")
    public OrgMembers getOrgMembers(@PathVariable Integer org_id) throws SQLException{
        return orgMasterService.getOrgMembers(org_id);
    }

    @GetMapping("api/org/getOrgAdmin/{org_id}")
    public ResponseEntity<?> getOrgAdmin(@PathVariable Integer org_id) throws SQLException{
        Integer success = orgMasterService.getOrgAdmin(org_id);
        return ResponseEntity.ok().body(success);
    }
}
