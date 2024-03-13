package com.oslab.agent.model.transfer.orgDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DelProjectDto {
    private Long org_id;
    private Long project_id;
}
