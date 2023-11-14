package com.oslab.agent.repository.organization.mapper;

import com.oslab.agent.model.transfer.orgDto.AddProjectDto;
import com.oslab.agent.model.transfer.orgDto.DelProjectDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgProjectMapper {
    Integer addProject(AddProjectDto addProjectDto);
    boolean deleteProject(DelProjectDto delProjectDto);
}
