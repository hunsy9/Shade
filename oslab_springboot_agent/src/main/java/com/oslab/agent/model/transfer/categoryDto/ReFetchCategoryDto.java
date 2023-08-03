package com.oslab.agent.model.transfer.categoryDto;

import com.oslab.agent.model.entity.orgEntity.OrgServer;
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
public class ReFetchCategoryDto {
    private Map<String, List<String>> category;
    private Map<String, List<OrgServer>> project_server;
}
