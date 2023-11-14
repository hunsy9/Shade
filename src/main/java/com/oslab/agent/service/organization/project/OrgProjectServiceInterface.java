package com.oslab.agent.service.organization.project;

import com.oslab.agent.model.transfer.orgDto.AddProjectDto;
import com.oslab.agent.model.transfer.orgDto.DelProjectDto;

public interface OrgProjectServiceInterface {
    Integer addProject(AddProjectDto addProjectDto);
    boolean deleteProject(DelProjectDto delProjectDto);
}
