package com.oslab.agent.model.transfer.contributorDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ContributorDto {
    private Long org_id;
    private String contributor_email;
}
