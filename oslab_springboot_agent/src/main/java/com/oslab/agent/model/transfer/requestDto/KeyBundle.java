package com.oslab.agent.model.transfer.requestDto;

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
}
