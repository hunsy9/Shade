package com.oslab.agent.model.transfer.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExitDto {
    private String wsKey;
    private String thKey;
}
