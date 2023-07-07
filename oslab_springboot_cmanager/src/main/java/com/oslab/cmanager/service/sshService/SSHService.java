package com.oslab.cmanager.service.sshService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oslab.cmanager.model.transfer.SSHDto.ServerDetailDto;
import com.oslab.cmanager.model.transfer.SSHDto.Command;
import com.oslab.cmanager.model.transfer.SSHDto.ConnectingInfo;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import com.oslab.cmanager.util.SshUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SSHService implements SSHServiceInterface{

    private final SshUtil sshUtil;

    private final RedisTemplate<String,String> redisTemplate;

    private final ObjectMapper objectMapper;

    public ServerDetailDto getServerDetailByServerId(int server_id) throws JsonProcessingException{

        String result = redisTemplate.opsForValue().get("Server:" + server_id);

        ServerDetailDto serverDetailDto = objectMapper.readValue(result, ServerDetailDto.class);

        return serverDetailDto;
    }

    public void makeNewSSHThread(ConnectingInfo connectingInfo){

        ServerDetailDto serverDetail = null;
        try {
            serverDetail = getServerDetailByServerId(connectingInfo.getServer_id());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if(serverDetail == null){
            return;
        }

        StartingInfo startingInfo = StartingInfo.builder()
                .username(serverDetail.getUsername())
                .host(serverDetail.getHost())
                .port(serverDetail.getPort())
                .password(serverDetail.getPassword())
                .user_id(connectingInfo.getUser_id())
                .server_id(connectingInfo.getServer_id())
                .room_id(connectingInfo.getRoom_id())
                .build();

        sshUtil.makeNewSSHThread(startingInfo);
    }

    public String command(Command command) {
        return sshUtil.sendCommand(command.getKey(), command.getCommand());
    }
}
