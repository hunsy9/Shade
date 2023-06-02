package com.oslab.agent.service.server;

import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;

import java.sql.SQLException;

public interface ServerServiceInterface {
    boolean addNewServer(AddServerDto addServerDto) throws SQLException;
    boolean editServerInfo(EditServerDto editServerDto) throws SQLException;
    boolean deleteServer(String server_id) throws SQLException;
}
