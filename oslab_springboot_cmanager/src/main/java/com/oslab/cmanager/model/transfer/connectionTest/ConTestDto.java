package com.oslab.cmanager.model.transfer.connectionTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConTestDto {
    private String username;
    private String host;
    private String port;
    private String password;
}
