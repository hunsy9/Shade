package com.oslab.agent.model.transfer.contributorDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegContributorDto {
    private Long user_id;
    private Long org_id;
    private String contributor_email;
}
