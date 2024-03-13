package com.oslab.cmanager.model.transfer.SSHDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    private String key;
    private String command;
}
