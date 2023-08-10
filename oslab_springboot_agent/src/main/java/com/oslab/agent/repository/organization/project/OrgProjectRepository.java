package com.oslab.agent.repository.organization.project;

import com.oslab.agent.model.transfer.orgDto.AddProjectDto;
import com.oslab.agent.model.transfer.orgDto.DelProjectDto;
import com.oslab.agent.repository.organization.mapper.OrgProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrgProjectRepository {
    private final OrgProjectMapper orgProjectMapper;

    public Integer addProject(AddProjectDto addProjectDto){
        orgProjectMapper.addProject(addProjectDto);
        return addProjectDto.getProject_id();
    }

    public boolean deleteProject(DelProjectDto delProjectDto){
        return orgProjectMapper.deleteProject(delProjectDto);
    }

}
