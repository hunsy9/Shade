package com.oslab.agent.service.organization.project;

import com.oslab.agent.model.transfer.orgDto.AddProjectDto;
import com.oslab.agent.model.transfer.orgDto.DelProjectDto;
import com.oslab.agent.repository.organization.mapper.OrgProjectMapper;
import com.oslab.agent.repository.organization.project.OrgProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrgProjectService implements OrgProjectServiceInterface{

    private final OrgProjectRepository orgProjectRepository;

    public Integer addProject(AddProjectDto addProjectDto) {
        return orgProjectRepository.addProject(addProjectDto);
    }

    public boolean deleteProject(DelProjectDto delProjectDto) {
        return orgProjectRepository.deleteProject(delProjectDto);
    }
}
