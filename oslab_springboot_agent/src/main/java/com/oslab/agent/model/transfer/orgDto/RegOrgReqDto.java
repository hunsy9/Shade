package com.oslab.agent.model.transfer.orgDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegOrgReqDto {
    private String org_name;
    private Long org_admin_id;
}
