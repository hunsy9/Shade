package com.oslab.agent.model.entity.orgEntity;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfo {

    private Integer project_id;
    private String project_name;
    private Integer category_id;
    private String prev;
    private String next;

    @Nullable
    private Integer server_id;
    @Nullable
    private String server_name;
    @Nullable
    private String server_desc;
}
