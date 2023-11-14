package com.oslab.cmanager.controller;

import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.service.sshService.SSHService;
import com.oslab.cmanager.service.webSocketService.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WebSocketController {
    private final WebSocketService webSocketService;

    private final SSHService sshService;

    @GetMapping("api/wsService/makews/{user_id}/{server_id}")
    public String createPtyRoom(@PathVariable int user_id, @PathVariable int server_id){
        return webSocketService.makeNewPtySession(user_id, server_id);
    }

    @MessageMapping(value = "/chat/message")
    public void message(Command command){
        sshService.command(command);
    }

}
