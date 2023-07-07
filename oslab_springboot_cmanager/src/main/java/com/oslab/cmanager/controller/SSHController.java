package com.oslab.cmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.JSchException;
import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.ConnectingInfo;
import com.oslab.cmanager.model.transfer.SSHDto.ServerDetailDto;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import com.oslab.cmanager.service.sshService.SSHService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class SSHController {
    private final SSHService sshService;

    @GetMapping("api/sshService/getServerDetailByServerId/{server_id}")
    public ServerDetailDto getServerDetailByServerId(@PathVariable int server_id) throws JsonProcessingException{
        return sshService.getServerDetailByServerId(server_id);
    }

    @PostMapping("api/sshService/startChannel")
    public void startChannel(@RequestBody ConnectingInfo connectingInfo) throws JSchException, JsonProcessingException {
        sshService.makeNewSSHThread(connectingInfo);
    }

    @PostMapping("api/sshService/command")
    public String command(@RequestBody Command command) throws JSchException, IOException {
        return sshService.command(command);
    }

}
