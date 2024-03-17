package com.oslab.agent.service.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oslab.agent.model.entity.orgEntity.OrgServer;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.model.transfer.serverDto.ServerDetailDto;
import com.oslab.agent.repository.server.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerService implements ServerServiceInterface{
    private final ServerRepository serverRepository;

    private final RedisTemplate<String,String> redisTemplate;

    public int addNewServer(MultipartFile keyfile, AddServerDto addServerDto) throws SQLException, JsonProcessingException {
        try {
            return serverRepository.addNewServer(keyfile,addServerDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean editServerInfo(EditServerDto editServerDto) throws SQLException{
        return serverRepository.editServerInfo(editServerDto);
    }

    public boolean deleteServer(String server_id) throws SQLException{
        String redisKey = "Server:"+server_id;
        redisTemplate.delete(redisKey);
        return serverRepository.deleteServer(server_id);
    }

    public List<OrgServer> reFetchServer(Integer org_id, Integer category_id) throws SQLException{
        return serverRepository.reFetchServer(org_id,category_id);
    }
}
