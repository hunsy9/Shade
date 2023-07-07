package com.oslab.cmanager.service.webSocketService;


import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
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

    public String makeNewWebSocketSession(int user_id, int server_id){
        SshConnectionRoom room = SshConnectionRoom.create(user_id, server_id);

        String room_id = room.getRoom_id();
        String key = user_id + ":" + server_id + ":" +room_id;
        sshRooms.put(key,room);

        return room.getRoom_id();
    }
}
