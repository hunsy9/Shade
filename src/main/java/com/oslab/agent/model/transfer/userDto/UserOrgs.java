package com.oslab.agent.model.transfer.userDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserOrgs {
    private String org_id;
    private String org_name;
}
