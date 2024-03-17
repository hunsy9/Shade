package com.oslab.agent.model.transfer.serverDto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionTestDto {
    private String username;
    private String host;
    private int port;

    @Nullable
    private String password;

    @Nullable
    private String fileDir;

}
