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
public class RegOrgReqDto {
    @Nullable
    private Long org_id;
    private String org_name;
    private Long org_admin_id;
}
