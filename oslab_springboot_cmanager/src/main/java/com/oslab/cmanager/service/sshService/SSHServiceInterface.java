package com.oslab.cmanager.service.sshService;

import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;

public interface SSHServiceInterface {
    public void makeNewSSHThread(StartingInfo startingInfo);
    public String command(Command command);
}
