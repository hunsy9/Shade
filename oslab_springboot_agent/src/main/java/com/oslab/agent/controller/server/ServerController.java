package com.oslab.agent.controller.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oslab.agent.model.entity.orgEntity.OrgServer;
import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.model.transfer.serverDto.ServerDetailDto;
import com.oslab.agent.service.server.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins="*")
public class ServerController {
    private final ServerService serverService;

    @PostMapping("/api/server/add")
    public int addNewServer(@RequestPart(value = "keyfile", required = false) MultipartFile keyfile, @RequestPart("serverDetail") AddServerDto addServerDto) throws SQLException, JsonProcessingException {
        System.out.println(keyfile.toString());
        return serverService.addNewServer(keyfile, addServerDto);
    }

    @PostMapping("/api/server/edit")
    public boolean editServerInfo(@RequestBody EditServerDto editServerDto) throws SQLException {
        return serverService.editServerInfo(editServerDto);
    }

    @PostMapping("/api/server/delete")
    public boolean deleteServer(@RequestParam("server_id") String server_id) throws SQLException {
        return serverService.deleteServer(server_id);
    }

    @GetMapping("/api/server/reFetchServer/{org_id}/{category_id}")
    public List<OrgServer> reFetchServer(@PathVariable Integer org_id, @PathVariable Integer category_id) throws SQLException {
        return serverService.reFetchServer(org_id, category_id);
    }

}
