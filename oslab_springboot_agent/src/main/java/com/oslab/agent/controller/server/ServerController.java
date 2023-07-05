package com.oslab.agent.controller.server;

import com.oslab.agent.model.transfer.contributorDto.ContributorDto;
import com.oslab.agent.model.transfer.serverDto.AddServerDto;
import com.oslab.agent.model.transfer.serverDto.EditServerDto;
import com.oslab.agent.service.server.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins="*")
public class ServerController {
    private final ServerService serverService;

    @PostMapping("/api/server/add")
    public boolean addNewServer(@RequestBody AddServerDto addServerDto) throws SQLException {
        return serverService.addNewServer(addServerDto);
    }

    @PostMapping("/api/server/edit")
    public boolean editServerInfo(@RequestBody EditServerDto editServerDto) throws SQLException {
        return serverService.editServerInfo(editServerDto);
    }

    @PostMapping("/api/server/delete")
    public boolean deleteServer(@RequestParam("server_id") String server_id) throws SQLException {
        return serverService.deleteServer(server_id);
    }
}
