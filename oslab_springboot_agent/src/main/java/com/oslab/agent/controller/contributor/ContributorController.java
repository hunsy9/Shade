package com.oslab.agent.controller.contributor;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.service.contributor.ContributorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ContributorController {
    private final ContributorService contributorService;

    @PostMapping("/api/contributor/add")
    public boolean addContributor(@RequestBody ContributorDto contributorDto) {
        return contributorService.addContributor(contributorDto);
    }
    @PostMapping("/api/contributor/register")
    public boolean registerContributor(@RequestBody ContributorDto contributorDto)
    {
        return contributorService.registerContributor(contributorDto);
    }
    @DeleteMapping("/api/contributor/delete")
    public boolean deleteContributor(@RequestBody ContributorDto contributorDto) {
        return contributorService.deleteContributor(contributorDto);
    }
    @PutMapping("/api/contributor/editEmail")
    public boolean editContributorEmail(@RequestBody ContributorDto contributorDto) {
        return contributorService.editContributorEmail(contributorDto);
    }
    @PutMapping("/api/contributor/editPriv")
    public boolean editPrivileges(){return false;}
}
