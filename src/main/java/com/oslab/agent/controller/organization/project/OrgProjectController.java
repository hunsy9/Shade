package com.oslab.agent.controller.organization.project;

import com.oslab.agent.model.transfer.orgDto.AddProjectDto;
import com.oslab.agent.service.organization.project.OrgProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@Slf4j
@CrossOrigin(origins="*")
@RequiredArgsConstructor
public class OrgProjectController {
    private final OrgProjectService orgProjectService;

    @PostMapping("api/project/addProject")
    public ResponseEntity<Integer> addProject(@RequestBody AddProjectDto addProjectDto) throws SQLException {
        Integer newProjectId = orgProjectService.addProject(addProjectDto);
        return ResponseEntity.ok().body(newProjectId) ;
    }
}
