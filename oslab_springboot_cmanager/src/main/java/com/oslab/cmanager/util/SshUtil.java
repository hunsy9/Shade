package com.oslab.cmanager.util;

import com.jcraft.jsch.*;
import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.ExitDto;
import com.oslab.cmanager.service.webSocketService.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
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

    public void makeNewSSHThread(String key, String webSocketKey, String password){
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

    public ResponseEntity<Boolean> exitShell(ExitDto exitDto) {
        String wsKey = exitDto.getWsKey();
        String thKey = exitDto.getThKey();

        Session session = keyToSessionMap.get(thKey);
        ChannelShell channelExec = keyToChannelMap.get(thKey);
        SshThread sshThread = (SshThread) keyToSshThread.get(thKey);

        sshThread.terminateThread();
        if (session != null) session.disconnect();
        if (channelExec != null) channelExec.disconnect();

        boolean success = sshThread.getState()== Thread.State.TERMINATED;

        if(success){
            System.out.println("success");
            keyToSessionMap.remove(thKey);
            keyToChannelMap.remove(thKey);
            keyToSshThread.remove(thKey);
            webSocketService.terminateWsRoom(wsKey);
            return ResponseEntity.ok().body(true);
        }
        else{
            return ResponseEntity.ok().body(false);
        }
    }
}
