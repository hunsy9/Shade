package com.oslab.cmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.JSchException;
import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.*;
import com.oslab.cmanager.service.sshService.SSHService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping ("api/sshService/generateKey")
    KeyBundle generateKey(@RequestBody ConnectingInfo connectingInfo){
        return sshService.generateKey(connectingInfo);
    }

    @PostMapping("api/sshService/startChannel")
    public void startChannel(@RequestBody KeyBundle keyBundle){
        sshService.makeNewSSHThread(keyBundle);
    }

    @PostMapping("api/sshService/command")
    public String command(@RequestBody Command command) throws JSchException, IOException {
        return sshService.command(command);
    }
    @PostMapping("api/sshService/exitShell")
    public ResponseEntity<Boolean> exitShell(@RequestBody ExitDto exitDto){
        return sshService.exitShell(exitDto);
    }

}
