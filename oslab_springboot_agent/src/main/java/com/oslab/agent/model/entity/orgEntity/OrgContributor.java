package com.oslab.agent.model.entity.orgEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgContributor {
    private int user_id;
    private String contributor_email;
}
