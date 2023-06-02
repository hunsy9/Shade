package com.oslab.agent.model.transfer.userDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupReqDto {
    private String user_email;
    private String user_password;
    private String user_name;
}
