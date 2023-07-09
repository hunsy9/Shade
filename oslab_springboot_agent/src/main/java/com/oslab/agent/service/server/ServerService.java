package com.oslab.agent.service.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.model.transfer.serverDto.ServerDetailDto;
import com.oslab.agent.repository.server.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerService implements ServerServiceInterface{
    private final ServerRepository serverRepository;

    public int addNewServer(AddServerDto addServerDto) throws SQLException, JsonProcessingException {
        return serverRepository.addNewServer(addServerDto);
    }


    public boolean editServerInfo(EditServerDto editServerDto) throws SQLException{
        return serverRepository.editServerInfo(editServerDto);
    }

    public boolean deleteServer(String server_id) throws SQLException{
        return serverRepository.deleteServer(server_id);
    }
}
