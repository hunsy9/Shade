package com.oslab.agent.model.entity.orgEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgMembers {
    private Contributor admin_contributor;
    private List<Contributor> contributors;
    private List<Contributor> pending_contributors;
}
