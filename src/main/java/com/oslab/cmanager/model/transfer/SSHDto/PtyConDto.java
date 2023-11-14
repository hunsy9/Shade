package com.oslab.cmanager.model.transfer.SSHDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PtyConDto {
    private Long user_id;
    private Integer server_id;
    private String nameSpace;
}
