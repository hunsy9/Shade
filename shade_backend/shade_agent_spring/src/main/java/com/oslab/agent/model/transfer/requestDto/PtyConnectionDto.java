package com.oslab.agent.model.transfer.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PtyConnectionDto {
    private Long user_id;
    private Integer server_id;
    private String nameSpace;
}

