package com.oslab.cmanager.model.transfer.SSHDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyBundle {
    private String threadKey;
    private String webSocketKey;
    private Long org_id;
    private Long server_id;
    private Boolean keyExistence;
}
