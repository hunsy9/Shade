package com.oslab.agent.model.transfer.mailDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStateDto {
    private Long org_id;
    private Long user_id;
    private String state;
}
