package com.oslab.agent.repository.server;

import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.repository.server.mapper.ServerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ServerRepository {
    private final ServerMapper serverMapper;

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
