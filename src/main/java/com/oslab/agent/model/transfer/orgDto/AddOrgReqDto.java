package com.oslab.agent.model.transfer.orgDto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddOrgReqDto {
    private Long org_id;
    private Long contributor_id;
    private String contributor_email;
    @Nullable
    private String state = "contributor";
}
