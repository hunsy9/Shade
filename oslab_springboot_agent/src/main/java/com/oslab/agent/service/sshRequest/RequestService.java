package com.oslab.agent.service.sshRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oslab.agent.model.transfer.requestDto.*;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {
    public String getWebSocketUrl(int user_id, int server_id) {
        String url = "http://localhost:8082/api/wsService/makews/" + user_id + "/" + server_id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public ResponseEntity<KeyBundle> generateKey(ConnectingDto connectingDto) {
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

    public void connectRoom(KeyBundle keyBundle) {
        String url = "http://localhost:8082/api/sshService/startChannel";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, keyBundle, String.class);
    }

    public ResponseEntity<Boolean> command(CommandDto commandDto) {
        String url = "http://localhost:8082/api/sshService/command";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject commandJson = new JSONObject();
        commandJson.put("key", commandDto.getKey());
        commandJson.put("command", commandDto.getCommand());
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, commandDto, Boolean.class);
        return response;
    }

    public ResponseEntity<Boolean> exitShell(ExitDto exitDto) {
        String url = "http://localhost:8082/api/sshService/exitShell";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(exitDto.getWsKey());
        System.out.println(exitDto.getThKey());

        JSONObject exitJson = new JSONObject();
        exitJson.put("wsKey", exitDto.getWsKey());
        exitJson.put("thKey", exitDto.getThKey());
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, exitJson, Boolean.class);
        return response;
    }

    public ResponseEntity<?> sshConnectionTest(MultipartFile keyfile, ConTestDto server) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        if(keyfile==null){
            body.add("keyfile", null);
        }else{
            body.add("keyfile", keyfile.getResource());
        }
        body.add("serverDetail", server);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(body, headers);

        return restTemplate.exchange(
                "http://localhost:8082/api/sshService/test1",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
    }

}

