package com.oslab.agent.model.transfer.orgDto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectDto {
    private Long org_id;
    private String project_name;
    @Nullable
    private Integer project_id;
}
