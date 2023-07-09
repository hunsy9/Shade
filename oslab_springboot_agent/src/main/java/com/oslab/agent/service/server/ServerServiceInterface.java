package com.oslab.agent.service.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;

import java.sql.SQLException;

public interface ServerServiceInterface {
    int addNewServer(AddServerDto addServerDto) throws SQLException, JsonProcessingException;
    boolean editServerInfo(EditServerDto editServerDto) throws SQLException;
    boolean deleteServer(String server_id) throws SQLException;
}
