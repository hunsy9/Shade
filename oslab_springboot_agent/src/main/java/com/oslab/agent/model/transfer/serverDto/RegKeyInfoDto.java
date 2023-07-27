package com.oslab.agent.model.transfer.serverDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegKeyInfoDto {
    private Long org_id;
    private Long server_id;
}
