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

    private void addNewServerToCloud(ServerDetailDto serverDetailDto, MultipartFile keyfile, Long org_id) throws JsonProcessingException {
        ValueOperations<String, String> newServerDetail = redisTemplate.opsForValue();
        String key = String.format("Server:%s", serverDetailDto.getServer_id());

        String serverDetail = objectMapper.writeValueAsString(serverDetailDto);

        newServerDetail.set(key, serverDetail);

        if (serverDetailDto.getKeyExistence()) {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            HttpHeaders headers = new HttpHeaders();
            RestTemplate restTemplate = new RestTemplate();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            body.add("keyfile", keyfile.getResource());
            RegKeyInfoDto regKeyInfoDto = RegKeyInfoDto.builder()
                    .org_id(org_id)
                    .server_id(serverDetailDto.getServer_id())
                    .build();
            body.add("regKeyInfo", regKeyInfoDto);
            System.out.println("dd");
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            System.out.println("aaa");

            ResponseEntity<?> res = restTemplate.exchange(
                    "http://localhost:8082/api/sshService/registerKeyfile",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
        }
    }

    public int addNewServer(MultipartFile keyfile, AddServerDto addServerDto) throws SQLException, JsonProcessingException {
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
