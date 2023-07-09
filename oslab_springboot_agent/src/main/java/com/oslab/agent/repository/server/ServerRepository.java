package com.oslab.agent.repository.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oslab.agent.model.entity.orgEntity.OrgServer;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.model.transfer.serverDto.ServerDetailDto;
import com.oslab.agent.repository.server.mapper.ServerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ServerRepository {
    private final ServerMapper serverMapper;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private void addNewServerToCloud(ServerDetailDto serverDetailDto) throws JsonProcessingException {
        ValueOperations<String,String> newServerDetail = redisTemplate.opsForValue();
        String key = String.format("Server:%s", serverDetailDto.getServer_id());

        String serverDetail = objectMapper.writeValueAsString(serverDetailDto);

        newServerDetail.set(key, serverDetail);
    }

    public int addNewServer(AddServerDto addServerDto) throws SQLException, JsonProcessingException {
        serverMapper.addNewServer(addServerDto);
        ServerDetailDto serverDetailDto = ServerDetailDto.builder()
                .server_id(addServerDto.getServer_id())
                .host(addServerDto.getHost())
                .username(addServerDto.getUsername())
                .password(addServerDto.getPassword())
                .port(addServerDto.getPort())
                .build();
        addNewServerToCloud(serverDetailDto);
        return 1;
    }

    public boolean editServerInfo(EditServerDto editServerDto) throws SQLException{
        return serverMapper.editServerInfo(editServerDto);
    }

    public boolean deleteServer(String server_id) throws SQLException{
        return serverMapper.deleteServer(server_id);
    }

    public List<OrgServer> reFetchServer(Integer org_id, Integer category_id) throws SQLException{
        return serverMapper.reFetchServer(org_id, category_id);
    }
}
