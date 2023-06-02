package com.oslab.agent.model.transfer.orgDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgReqDto {
    private Long user_id;
    private Long org_id;
}
