package com.oslab.cmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.oslab.cmanager.service.containerManage.ContainerServiceImpl;
import com.oslab.cmanager.util.SshUtil;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSException;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContainerController {

    private final ContainerServiceImpl containerService;

    @GetMapping("api/container/create")
    public String createContainer(/*@RequestBody*/) { return containerService.createContainer(); }

    @GetMapping("api/container/start")
//    public void startContainer(/*@PathVariable String container_name*/) { containerService.startContainer(/*container_name*/); }

    public void deleteContainer(){
        containerService.deleteContainer();
    }

}
