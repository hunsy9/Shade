package com.oslab.agent.model.entity.orgEntity;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Nullable
public class OrgServer {
    private Integer server_id;
    private String server_name;
    private String server_desc;
}
