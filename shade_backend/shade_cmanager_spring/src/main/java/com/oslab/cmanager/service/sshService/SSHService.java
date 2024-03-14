package com.oslab.cmanager.service.sshService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oslab.cmanager.model.enums.TestResult;
import com.oslab.cmanager.model.transfer.SSHDto.*;
import com.oslab.cmanager.model.transfer.connectionTest.ConnectionTestDto;
import com.oslab.cmanager.util.SshTestThread;
import com.oslab.cmanager.util.SshUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private Map<String,String> filenamesMap = new HashMap<>();

    public ServerDetailDto getServerDetailByServerId(Integer server_id) throws JsonProcessingException{

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
                .keyExistence(serverDetail.getKeyExistence())
                .build();
    }

    public void startPty(PtyConDto ptyConDto) throws JsonProcessingException {
        ServerDetailDto serverDetailDto = getServerDetailByServerId(ptyConDto.getServer_id());
        System.out.println(serverDetailDto.toString());
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:3000/startSocketRoom";
        JSONObject detailJson = new JSONObject();

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

    public void makeNewSSHThread(KeyBundle keyBundle){
        String threadKey = keyBundle.getThreadKey();
        String webSocketKey = keyBundle.getWebSocketKey();
        String password = pwds.get(threadKey);
        Long org_id = keyBundle.getOrg_id();
        Long server_id = keyBundle.getServer_id();
        Boolean keyExistence = keyBundle.getKeyExistence();
        pwds.remove(threadKey);
        sshUtil.makeNewSSHThread(threadKey, webSocketKey, password, org_id, server_id,keyExistence);
    }

    public String command(Command command) {
        return sshUtil.sendCommand(command.getKey(), command.getCommand());
    }

    public ResponseEntity<Boolean> exitShell(ExitDto exitDto){
        return sshUtil.exitShell(exitDto);
    }

    public ResponseEntity<?> startTestChannel(ConnectionTestDto connectionTestDto){
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

    public String fileStoreBuffer(MultipartFile keyfile) throws IOException {
        String dir = "/home/opc/oidc/keyBuffer";
        String fullFilePath = dir + File.separator + keyfile.getOriginalFilename();
        Path path = Paths.get(fullFilePath).toAbsolutePath();
        keyfile.transferTo(path.toFile());
        return path.toString();
    }

    public String keyFileStore(MultipartFile keyfile, int org_id, int server_id) throws IOException {
        String dir = "/home/opc/oidc/key/"+org_id+"/"+server_id;
        File folder = new File(dir);
        if(!folder.exists()){
            try {
                folder.mkdirs();
                log.info(dir+" keyFile folder generated!");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String fullPath = dir + File.separator + keyfile.getOriginalFilename();
        Path path = Paths.get(fullPath).toAbsolutePath();
        keyfile.transferTo(path.toFile());
        return path.toString();
    }

    private void fileDel(){

    }

}
