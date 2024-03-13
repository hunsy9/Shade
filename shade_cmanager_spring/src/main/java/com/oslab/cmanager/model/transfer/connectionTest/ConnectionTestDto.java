package com.oslab.cmanager.model.transfer.connectionTest;

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
