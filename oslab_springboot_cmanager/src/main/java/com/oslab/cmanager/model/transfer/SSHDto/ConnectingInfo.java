package com.oslab.cmanager.model.transfer.SSHDto;

import lombok.Data;

@Data
public class ConnectingInfo {
    private int user_id;
    private int server_id;
    private String room_id;
}
