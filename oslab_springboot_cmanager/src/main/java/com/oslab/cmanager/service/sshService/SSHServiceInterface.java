package com.oslab.cmanager.service.sshService;

import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.ConnectingInfo;

public interface SSHServiceInterface {

    public void makeNewSSHThread(ConnectingInfo connectingInfo);
    public String command(Command command);
}
