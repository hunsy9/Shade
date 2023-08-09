package com.oslab.cmanager.model.transfer.SSHDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExitDto {
    private String wsKey;
    private String thKey;
}
