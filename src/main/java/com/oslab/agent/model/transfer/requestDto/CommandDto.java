package com.oslab.agent.model.transfer.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommandDto {
    private String key;
    private String command;
}
