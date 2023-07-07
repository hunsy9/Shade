package com.oslab.cmanager.util;

import com.jcraft.jsch.*;
import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import com.oslab.cmanager.service.sshService.SSHService;
import com.oslab.cmanager.service.webSocketService.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SshUtil {
    private Map<String, Session> keyToSessionMap = new HashMap<>();
    private Map<String, ChannelShell> keyToChannelMap = new HashMap<>();
    private Map<String, Thread> keyToSshThread = new HashMap<>();
    private Map<String, WebSocketSession> keyToWebsocketSession = new HashMap<>();

    private final SimpMessageSendingOperations sendingOperations;

    private final WebSocketService webSocketService;

    public void setKeyToWebsocketSession(String key, WebSocketSession webSocketSession){
        keyToWebsocketSession.put(key,webSocketSession);
    }
    public void setKeyToSessionMap(String key, Session session){
        keyToSessionMap.put(key,session);
    }

    public void setKeyToChannelMap(String key, ChannelShell channelShell){
        keyToChannelMap.put(key,channelShell);
    }

    public void makeNewSSHThread(StartingInfo startingInfo){
        String username = startingInfo.getUsername();
        String host = startingInfo.getHost();
        int port = startingInfo.getPort();
        String password = startingInfo.getPassword();

        int user_id = startingInfo.getUser_id();
        int server_id = startingInfo.getServer_id();
        String room_id = startingInfo.getRoom_id();

        String key = username + ":" + host + ":" + port;
        String webSocketKey = user_id + ":" + server_id + ":"+ room_id;

        SshConnectionRoom sshConnectionRoom = webSocketService.getSshRooms().get(webSocketKey);

        SshThread sshThread = new SshThread(key, password, "", this, sshConnectionRoom, sendingOperations);
        keyToSshThread.put(key, sshThread);
        sshThread.run();
    }

    public String sendCommand(String key, String command){
        SshThread connection = (SshThread) keyToSshThread.get(key);
        connection.setCommand(command);
        return connection.requestCommand();
    }

    public void disConnectSSH(String key) {
        Session session = keyToSessionMap.get(key);
        ChannelShell channelExec = keyToChannelMap.get(key);
        if (session != null) session.disconnect();
        if (channelExec != null) channelExec.disconnect();
    }

}
