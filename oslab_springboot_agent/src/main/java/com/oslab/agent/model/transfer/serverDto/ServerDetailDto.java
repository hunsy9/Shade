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
    private String username;
    private String host;
    private int port;
    private String password;
}
