package com.oslab.agent.model.transfer.serverDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditServerDto {
    private Long server_id;
    private String server_name;
    private String server_desc;
}
