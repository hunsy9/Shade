package com.oslab.agent.controller.contributor;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.model.transfer.contributorDto.EditPrivDto;
import com.oslab.agent.model.transfer.contributorDto.RegContributorDto;
import com.oslab.agent.service.contributor.ContributorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class ContributorController {
    private final ContributorService contributorService;

    @GetMapping("/api/contributor/add")
    public boolean addContributor(@RequestParam("org_id") Long org_id, @RequestParam("user_id") Long user_id, @RequestParam("contributor_email") String email) throws SQLException {
        ContributorDto contributor = ContributorDto.builder().org_id(org_id).user_id(user_id).contributor_email(email).state("contributor").build();
        return contributorService.addContributor(contributor);
    }
    @PostMapping("/api/contributor/register")
    public boolean registerContributor(@RequestBody RegContributorDto regContributorDto) throws SQLException {
        return contributorService.registerContributor(regContributorDto);
    }
    @DeleteMapping("/api/contributor/delete")
    public boolean deleteContributor(@RequestBody ContributorDto contributorDto) throws SQLException {
        return contributorService.deleteContributor(contributorDto);
    }
    @PutMapping("/api/contributor/editEmail")
    public boolean editContributorEmail(@RequestBody ContributorDto contributorDto) throws SQLException {
        return contributorService.editContributorEmail(contributorDto);
    }
    @PutMapping("/api/contributor/editPriv")
    public boolean editPrivileges(List<EditPrivDto> editPrivDtos) throws SQLException {
        return contributorService.editPrivileges(editPrivDtos);
    }
}
