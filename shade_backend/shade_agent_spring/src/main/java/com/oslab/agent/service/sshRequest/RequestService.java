package com.oslab.agent.service.sshRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oslab.agent.model.transfer.enums.TestResult;
import com.oslab.agent.model.transfer.requestDto.*;
import com.oslab.agent.model.transfer.serverDto.ConnectionTestDto;
import com.oslab.agent.service.webSocketService.WebSocketService;
import com.oslab.agent.util.SshTestThread;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    private final WebSocketService webSocketService;

    private Map<String,String> pwds = new HashMap<>();
    public String getWebSocketUrl(int user_id, int server_id) {
        return webSocketService.makeNewPtySession(user_id, server_id);
    }

    public KeyBundle generateKey(ConnectingDto connectingDto) {

        ServerDetailDto serverDetail = null;
        try {
            serverDetail = getServerDetailByServerId(connectingDto.getServer_id());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String username = serverDetail.getUsername();
        String host = serverDetail.getHost();
        int port = serverDetail.getPort();
        String password = serverDetail.getPassword();

        int user_id = connectingDto.getUser_id();
        int server_id = connectingDto.getServer_id();
        String room_id = connectingDto.getRoom_id();

        String threadKey = username + ":" + host + ":" + port + ":" + room_id; //find Ssh Thread
        String webSocketKey = user_id + ":" + server_id + ":" + room_id; //find ws Room

        pwds.put(threadKey, password);
        KeyBundle keyBundle = KeyBundle.builder()
                .threadKey(threadKey)
                .webSocketKey(webSocketKey)
                .keyExistence(serverDetail.getKeyExistence())
                .build();

        return keyBundle;
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

        ConnectionTestDto connectionTestDto = ConnectionTestDto.builder()
                .port(Integer.parseInt(server.getPort()))
                .host(server.getHost())
                .username(server.getUsername())
                .password(server.getPassword())
                .build();

        if (!Objects.requireNonNull(keyfile.getOriginalFilename()).equals("nFile")){
            String dir = fileStoreBuffer(keyfile);
            connectionTestDto.setFileDir(dir);
        } else{
            connectionTestDto.setFileDir(String.valueOf(keyfile.getResource()));
        }

        Map<String, Object> m = new HashMap<>();

        SshTestThread sshTestThread = new SshTestThread(connectionTestDto);
        sshTestThread.run();
        TestResult testResult = sshTestThread.getTestSuccess();
        System.out.println(testResult);
        if(testResult == TestResult.SUCCESS){
            return ResponseEntity.status(HttpStatus.OK).body(m);
        }
        else if(testResult == TestResult.CONNECTIONERR){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(m);
        }
        return ResponseEntity.ok().body("");
    }

    public void startPty(PtyConnectionDto ptyConDto) throws JsonProcessingException {
        ServerDetailDto serverDetailDto = getServerDetailByServerId(ptyConDto.getServer_id());
        System.out.println(serverDetailDto.toString());
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://shade-pty:3000/startSocketRoom";
        JSONObject detailJson = new JSONObject();
        log.info("sdfasdf??"+ ptyConDto);
        detailJson.put("user_id", ptyConDto.getUser_id());
        detailJson.put("server_id", ptyConDto.getServer_id());
        detailJson.put("nameSpace", ptyConDto.getNameSpace());
        detailJson.put("username", serverDetailDto.getUsername());
        detailJson.put("host", serverDetailDto.getHost());
        detailJson.put("port", serverDetailDto.getPort());
        detailJson.put("password", serverDetailDto.getPassword());
        detailJson.put("keyExistence", serverDetailDto.getKeyExistence());

        restTemplate.postForEntity(url, detailJson, Void.class);
    }

    public ServerDetailDto getServerDetailByServerId(Integer server_id) throws JsonProcessingException{

        String result = redisTemplate.opsForValue().get("Server:" + server_id);

        return objectMapper.readValue(result, ServerDetailDto.class);
    }

    public String fileStoreBuffer(MultipartFile keyfile) throws IOException {
        String dir = "/home/opc/oidc/keyBuffer";
        String fullFilePath = dir + File.separator + keyfile.getOriginalFilename();
        Path path = Paths.get(fullFilePath).toAbsolutePath();
        keyfile.transferTo(path.toFile());
        return path.toString();
    }

}

