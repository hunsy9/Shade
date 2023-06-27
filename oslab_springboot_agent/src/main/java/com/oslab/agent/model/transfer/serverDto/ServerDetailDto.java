package com.oslab.agent.model.transfer.serverDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerDetailDto {
    private String public_ip;
    private String private_ip;
    private String host;
    private String password;
    private String vpn_host;
}
