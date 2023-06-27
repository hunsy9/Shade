package com.oslab.agent.repository.server;

import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.model.transfer.serverDto.ServerDetailDto;
import com.oslab.agent.repository.server.mapper.ServerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ServerRepository {
    private final ServerMapper serverMapper;

    private final RedisTemplate<String, String> redisTemplate;

    private Long addNewServerToCloud(ServerDetailDto serverDetailDto){
        ListOperations<String,String> newServerDetail = redisTemplate.opsForList();
        String key = String.format("Server:%s", serverDetailDto.toString());
        return newServerDetail.rightPush(key, serverDetailDto.getPublic_ip());
    }

    public boolean addNewServer(AddServerDto addServerDto) throws SQLException {
        return serverMapper.addNewServer(addServerDto);
    }

    public boolean editServerInfo(EditServerDto editServerDto) throws SQLException{
        return serverMapper.editServerInfo(editServerDto);
    }

    public boolean deleteServer(String server_id) throws SQLException{
        return serverMapper.deleteServer(server_id);
    }
}
