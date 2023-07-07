package com.oslab.cmanager.model.transfer.SSHDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartingInfo {

    private String username;
    private String host;
    private int port;
    private String password;

    private int user_id;
    private int server_id;
    private String room_id;

}
