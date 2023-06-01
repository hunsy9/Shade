package com.oslab.agent.model.transfer;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private Integer user_id;
    private String user_email;
    private String user_password;
    private String user_name;
}
