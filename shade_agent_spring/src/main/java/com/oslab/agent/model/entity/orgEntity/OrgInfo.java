package com.oslab.agent.model.entity.orgEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgInfo {
    private List<OrgProject> projects;
    private List<Integer> org_user_privileges;
}
