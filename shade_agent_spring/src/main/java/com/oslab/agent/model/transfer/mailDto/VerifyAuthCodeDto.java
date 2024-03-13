package com.oslab.agent.model.transfer.mailDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyAuthCodeDto {
    private String mailKey;
    private String code;
}
