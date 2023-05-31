package com.oslab.agent.model.transfer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String userId;
    private String userEmail;
    private String userPassword;
    private String userName;
}
