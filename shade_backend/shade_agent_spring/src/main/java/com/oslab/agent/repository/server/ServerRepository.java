package com.oslab.agent.repository.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oslab.agent.model.entity.orgEntity.OrgServer;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.model.transfer.serverDto.RegKeyInfoDto;
import com.oslab.agent.model.transfer.serverDto.ServerDetailDto;
import com.oslab.agent.repository.server.mapper.ServerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ServerRepository {
    private final ServerMapper serverMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private String storeKeyFile(MultipartFile keyfile, Long org_id, Long server_id) throws IOException {
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

    private void addNewServerToCloud(ServerDetailDto serverDetailDto, MultipartFile keyfile, Long org_id) throws IOException {
        ValueOperations<String, String> newServerDetail = redisTemplate.opsForValue();
        String key = String.format("Server:%s", serverDetailDto.getServer_id());

        String serverDetail = objectMapper.writeValueAsString(serverDetailDto);

        newServerDetail.set(key, serverDetail);

        if (serverDetailDto.getKeyExistence()) {
            storeKeyFile(keyfile, org_id, serverDetailDto.getServer_id());
        }
    }

    public int addNewServer(MultipartFile keyfile, AddServerDto addServerDto) throws SQLException, IOException {
        System.out.println(keyfile.getOriginalFilename());
        serverMapper.addNewServer(addServerDto);
        ServerDetailDto serverDetailDto = ServerDetailDto.builder()
                .server_id(addServerDto.getServer_id())
                .host(addServerDto.getHost())
                .username(addServerDto.getUsername())
                .password(addServerDto.getPassword())
                .port(addServerDto.getPort())
                .keyExistence(keyfile.getOriginalFilename().equals("nFile") ? false : true)
                .build();
        addNewServerToCloud(serverDetailDto, keyfile, addServerDto.getOrg_id());
        return 1;
    }

    public boolean editServerInfo(EditServerDto editServerDto) throws SQLException {
        return serverMapper.editServerInfo(editServerDto);
    }

    public boolean deleteServer(String server_id) throws SQLException {
        return serverMapper.deleteServer(server_id);
    }

    public List<OrgServer> reFetchServer(Integer org_id, Integer category_id) throws SQLException {
        return serverMapper.reFetchServer(org_id, category_id);
    }
}
