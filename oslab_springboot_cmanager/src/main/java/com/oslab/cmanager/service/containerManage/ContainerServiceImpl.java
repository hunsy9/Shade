package com.oslab.cmanager.service.containerManage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {

    private final DockerClient dockerClient;
    @Override
    public void createContainer() {
        CreateContainerResponse container
                = dockerClient
                .createContainerCmd("ubuntu:20.04")
                .withCmd("/bin/bash")
                .withTty(true)
                .withName("abc")
                .exec();


    }

//    public void startContainer(String container_name) {
//        dockerClient.startContainerCmd(container.getId()).exec();
//    }
    @Override
    public void deleteContainer() {

    }

    private File getSSHKeyFile(){
        return null;
    }


}