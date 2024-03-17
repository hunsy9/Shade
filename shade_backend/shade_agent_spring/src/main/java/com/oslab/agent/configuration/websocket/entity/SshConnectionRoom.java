package com.oslab.agent.configuration.websocket.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SshConnectionRoom {
    private int user_id;
    private int server_id;
    private String room_id;

    public static SshConnectionRoom create(int user_id, int server_id){
        SshConnectionRoom sshConnectionRoom = new SshConnectionRoom();
        sshConnectionRoom.setUser_id(user_id);
        sshConnectionRoom.setServer_id(server_id);
        sshConnectionRoom.setRoom_id(UUID.randomUUID().toString());
        return sshConnectionRoom;
    }
}
