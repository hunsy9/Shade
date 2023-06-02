package com.oslab.agent.controller.organization.project;

import com.oslab.agent.model.transfer.orgDto.AddProjectDto;
import com.oslab.agent.service.organization.project.OrgProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrgProjectController {
    private final OrgProjectService orgProjectService;

    @PostMapping("api/project/addProject")
    public ResponseEntity<?> addProject(@RequestBody AddProjectDto addProjectDto) throws SQLException {
        boolean success = orgProjectService.addProject(addProjectDto);
        return success ? ResponseEntity.ok().body("Success") : ResponseEntity.internalServerError().body("Fail");
    }
}
