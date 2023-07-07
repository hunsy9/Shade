package com.oslab.cmanager.service.sshService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oslab.cmanager.model.transfer.SSHDto.*;
import com.oslab.cmanager.util.SshUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SSHService implements SSHServiceInterface{

    private final SshUtil sshUtil;

    private final RedisTemplate<String,String> redisTemplate;

    private final ObjectMapper objectMapper;

    private Map<String,String> pwds = new HashMap<>();

    public ServerDetailDto getServerDetailByServerId(int server_id) throws JsonProcessingException{

        String result = redisTemplate.opsForValue().get("Server:" + server_id);

        ServerDetailDto serverDetailDto = objectMapper.readValue(result, ServerDetailDto.class);

        return serverDetailDto;
    }

    public KeyBundle generateKey(ConnectingInfo connectingInfo){

        ServerDetailDto serverDetail = null;
        try {
            serverDetail = getServerDetailByServerId(connectingInfo.getServer_id());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        if(serverDetail == null){
//            return ;
//        }

        String username = serverDetail.getUsername();
        String host = serverDetail.getHost();
        int port = serverDetail.getPort();
        String password = serverDetail.getPassword();

        int user_id = connectingInfo.getUser_id();
        int server_id = connectingInfo.getServer_id();
        String room_id = connectingInfo.getRoom_id();

        String threadKey = username + ":" + host + ":" + port + ":" + room_id; //find Ssh Thread
        String webSocketKey = user_id + ":" + server_id + ":" + room_id; //find ws Room

        pwds.put(threadKey, password);

        return KeyBundle.builder()
                .threadKey(threadKey)
                .webSocketKey(webSocketKey)
                .build();
    }


    public void makeNewSSHThread(KeyBundle keyBundle){
        String threadKey = keyBundle.getThreadKey();
        String webSocketKey = keyBundle.getWebSocketKey();
        String password = pwds.get(threadKey);
        pwds.remove(threadKey);
        sshUtil.makeNewSSHThread(threadKey, webSocketKey, password);
    }

    public String command(Command command) {
        return sshUtil.sendCommand(command.getKey(), command.getCommand());
    }
}
