package com.oslab.cmanager.service.sshService;

import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import com.oslab.cmanager.util.SshUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SSHService implements SSHServiceInterface{

    private final SshUtil sshUtil;

    public void makeNewSSHThread(StartingInfo startingInfo){
        sshUtil.makeNewSSHThread(startingInfo);
    }

    public String command(Command command) {
        return sshUtil.sendCommand(command.getKey(), command.getCommand());
    }
}
