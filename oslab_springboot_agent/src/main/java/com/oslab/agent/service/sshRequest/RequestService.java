package com.oslab.agent.service.sshRequest;

import com.oslab.agent.model.transfer.requestDto.CommandDto;
import com.oslab.agent.model.transfer.requestDto.ConnectingDto;
import com.oslab.agent.model.transfer.requestDto.KeyBundle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {
    public String getWebSocketUrl(int user_id, int server_id){
        String url = "http://localhost:8082/api/wsService/makews/" + user_id +"/" + server_id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public ResponseEntity<KeyBundle> generateKey(ConnectingDto connectingDto){
        String url = "http://localhost:8082/api/sshService/generateKey";
        log.info("들어");
        RestTemplate restTemplate = new RestTemplate();
        JSONObject generatingJson = new JSONObject();
        generatingJson.put("user_id", connectingDto.getUser_id());
        generatingJson.put("server_id", connectingDto.getServer_id());
        generatingJson.put("room_id", connectingDto.getRoom_id());
        ResponseEntity<KeyBundle> response = restTemplate.postForEntity(url, generatingJson, KeyBundle.class);
        return response;
    }

    public void connectRoom(KeyBundle keyBundle){
        String url = "http://localhost:8082/api/sshService/startChannel";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject runningJson = new JSONObject();
        runningJson.put("threadKey", keyBundle.getThreadKey());
        runningJson.put("webSocketKey", keyBundle.getWebSocketKey());
        restTemplate.postForEntity(url, runningJson, String.class);
    }

    public ResponseEntity<Boolean> command(CommandDto commandDto){
        String url = "http://localhost:8082/api/sshService/command";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject commandJson = new JSONObject();
        commandJson.put("key", commandDto.getKey());
        commandJson.put("command" , commandDto.getCommand());
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, commandDto, Boolean.class);
        return response;
    }
}
