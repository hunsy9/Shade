package com.oslab.agent.service.webSocketService;


import com.oslab.agent.configuration.websocket.entity.SshConnectionRoom;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Data
public class WebSocketService {

    private Map<String, SshConnectionRoom> sshRooms = new HashMap<>();

    public String makeNewPtySession(int user_id, int server_id){
        SshConnectionRoom room = SshConnectionRoom.create(user_id, server_id);
        String room_id = room.getRoom_id();
        return room_id;
    }

    public SshConnectionRoom terminateWsRoom(String key){
        return sshRooms.remove(key);
    }
}
