package com.oslab.cmanager.service.sshService;

import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.ConnectingInfo;
import com.oslab.cmanager.model.transfer.SSHDto.KeyBundle;

public interface SSHServiceInterface {

    public KeyBundle generateKey(ConnectingInfo connectingInfo);
    public void makeNewSSHThread(KeyBundle keyBundle);
    public String command(Command command);
}
