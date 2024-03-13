package com.oslab.agent.model.transfer.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResDto {
    Integer user_id;
    String user_name;
    String user_email;
}
