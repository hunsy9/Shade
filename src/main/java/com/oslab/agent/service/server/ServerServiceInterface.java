package com.oslab.agent.service.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oslab.agent.model.entity.orgEntity.OrgServer;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface ServerServiceInterface {
    int addNewServer(MultipartFile keyfile, AddServerDto addServerDto) throws SQLException, JsonProcessingException;
    boolean editServerInfo(EditServerDto editServerDto) throws SQLException;
    boolean deleteServer(String server_id) throws SQLException;
    List<OrgServer> reFetchServer(Integer org_id, Integer category_id) throws SQLException;
}
