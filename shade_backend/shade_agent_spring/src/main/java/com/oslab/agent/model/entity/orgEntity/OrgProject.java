package com.oslab.agent.model.entity.orgEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrgProject {
    private Integer project_id;
    private String project_name;
    private Map<String, List<String>> category;
    private Map<String, List<OrgServer>> project_server;
}
