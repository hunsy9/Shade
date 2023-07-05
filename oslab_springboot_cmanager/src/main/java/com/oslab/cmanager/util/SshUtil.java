package com.oslab.cmanager.util;

import com.jcraft.jsch.*;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SshUtil {
    private Map<String, Session> keyToSessionMap = new HashMap<>();

    private Map<String, ChannelShell> keyToChannelMap = new HashMap<>();

    private Map<String, Thread> keyToSshThread = new HashMap<>();

    public void setKeyToSessionMap(String key, Session session){
        keyToSessionMap.put(key,session);
    }

    public void setKeyToChannelMap(String key, ChannelShell channelShell){
        keyToChannelMap.put(key,channelShell);
    }

    public void makeNewSSHThread(StartingInfo startingInfo){
        String username = startingInfo.getUsername();
        String host = startingInfo.getHost();
        int port = startingInfo.getPort();
        String password = startingInfo.getPassword();
        String key = username + ":" + host + ":" + port;
        SshThread sshThread = new SshThread(key, password, "", this);
        keyToSshThread.put(key, sshThread);
        sshThread.run();
    }

    public String sendCommand(String key, String command){
        SshThread connection = (SshThread) keyToSshThread.get(key);
        connection.setCommand(command);
        return connection.requestCommand();
    }

    public void disConnectSSH(String key) {
        Session session = keyToSessionMap.get(key);
        ChannelShell channelExec = keyToChannelMap.get(key);
        if (session != null) session.disconnect();
        if (channelExec != null) channelExec.disconnect();
    }

}
