package com.oslab.cmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.JSchException;
import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.*;
import com.oslab.cmanager.model.transfer.connectionTest.ConTestDto;
import com.oslab.cmanager.model.transfer.connectionTest.ConnectionTestDto;
import com.oslab.cmanager.service.sshService.SSHService;
import com.oslab.cmanager.service.webSocketService.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("api/sshService/startPty")
    public void startPty(@RequestBody PtyConDto ptyConDto) throws IOException {
        System.out.println(ptyConDto.toString());
        sshService.startPty(ptyConDto);
    }

    @PostMapping("api/sshService/command")
    public String command(@RequestBody Command command) throws JSchException, IOException {
        return sshService.command(command);
    }
    @PostMapping("api/sshService/exitShell")
    public ResponseEntity<Boolean> exitShell(@RequestBody ExitDto exitDto){
        return sshService.exitShell(exitDto);
    }

    @PostMapping("api/sshService/test1")
    public ResponseEntity<?> test(@RequestPart("keyfile") MultipartFile keyfile, @RequestPart("serverDetail") ConnectionTestDto server) throws IOException {
        if (!Objects.requireNonNull(keyfile.getOriginalFilename()).equals("nFile")){
            String dir = sshService.fileStoreBuffer(keyfile);
            server.setFileDir(dir);
        }
        return sshService.startTestChannel(server);
    }

    @PostMapping("api/sshService/registerKeyfile")
    public ResponseEntity<?> registerKeyFile(@RequestPart("keyfile") MultipartFile keyfile, @RequestPart("regKeyInfo") RegKeyInfoDto regKeyInfoDto) throws IOException {
        sshService.keyFileStore(keyfile, regKeyInfoDto.getOrg_id(), regKeyInfoDto.getServer_id());
        return ResponseEntity.ok().body("");
    }
}
