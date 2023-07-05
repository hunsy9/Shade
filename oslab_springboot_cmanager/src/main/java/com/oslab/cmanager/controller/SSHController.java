package com.oslab.cmanager.controller;

import com.jcraft.jsch.JSchException;
import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import com.oslab.cmanager.service.sshService.SSHService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class SSHController {

    private final SSHService sshService;

    @PostMapping("api/sshService/startChannel")
    public void startChannel(@RequestBody StartingInfo startingInfo) throws JSchException {
        sshService.makeNewSSHThread(startingInfo);
    }

    @PostMapping("api/sshService/command")
    public String command(@RequestBody Command command) throws JSchException, IOException {
        return sshService.command(command);
    }

}
