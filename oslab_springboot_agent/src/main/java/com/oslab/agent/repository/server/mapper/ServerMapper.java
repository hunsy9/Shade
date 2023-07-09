package com.oslab.agent.repository.server.mapper;

import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface ServerMapper {
    int addNewServer(AddServerDto addServerDto) throws SQLException;

    boolean editServerInfo(EditServerDto editServerDto) throws SQLException;

    boolean deleteServer(String server_id) throws SQLException;
}
