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
public class ContributorController {
    private final ContributorService contributorService;

    @PostMapping("/api/contributor/add")
    public boolean addContributor(@RequestBody ContributorDto contributorDto) throws SQLException {
        return contributorService.addContributor(contributorDto);
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
