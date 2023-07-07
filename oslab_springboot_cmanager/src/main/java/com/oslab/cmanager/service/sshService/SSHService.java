package com.oslab.cmanager.service.sshService;

import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import com.oslab.cmanager.util.SshUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SSHService implements SSHServiceInterface{

    private final SshUtil sshUtil;

    public void makeNewSSHThread(StartingInfo startingInfo){
        sshUtil.makeNewSSHThread(startingInfo);
    }

    public String command(Command command) {
        return sshUtil.sendCommand(command.getKey(), command.getCommand());
    }
}
