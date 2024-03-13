package com.oslab.agent.model.entity.orgEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contributor {
    private int user_id;
    private String contributor_email;
}
