package com.oslab.agent.service.sshRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Service
@RequiredArgsConstructor
public class RequestService {
    public String getWebSocketUrl(int user_id, int server_id){
        String url = "http://localhost:8082/api/wsService/makews/" + user_id +"/" + server_id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
