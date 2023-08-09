package com.oslab.cmanager.service.containerManage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {

    private final DockerClient dockerClient;
    @Override
//    public void createContainer() {
//        List<Container> containers = dockerClient.listContainersCmd().exec();
//        log.info(String.valueOf(containers));
////        CreateContainerResponse container
////                = dockerClient
////                .createContainerCmd("ubuntu:20.04")
////                .withCmd("/bin/bash")
////                .withTty(true)
////                .withName("abc")
////                .exec();
//
//        dockerClient.execCreateCmd("");
//
//    }

    public String createContainer() {
//        CreateContainerResponse container
//                = dockerClient
//                .createContainerCmd("ubuntu:20.04")
//                .withCmd("/bin/bash")
//                .withTty(true)
//                .withName("abc")
//                .exec();

//        dockerClient.startContainerCmd("5be73ca8caa1334a5f092f0531df61036265aa7aba137cd898475f58a2bbfb95").exec();

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();


        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd("f1ed53e77afb21e0c3838f4d979ffe25c33a89245b6de03606271ea0c3de97c2")
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withCmd("sshpass","-p", "dnflRlfl", "ssh","-T", "root@haict.onthe.live")
                .exec();

//        ExecCreateCmdResponse execCreateCmdResponse1 = dockerClient.execCreateCmd("f1ed53e77afb21e0c3838f4d979ffe25c33a89245b6de03606271ea0c3de97c2")
//                .withAttachStdout(true)
//                .withAttachStderr(true)
//                .withCmd("ls", "-al")
//                .exec();

        try {
            dockerClient.execStartCmd(execCreateCmdResponse.getId()).exec(
                    new ExecStartResultCallback(stdout, stderr)).awaitCompletion();
            System.out.println(stdout);
            System.out.println(stderr);
        }
        catch(InterruptedException e) {

        }

//        try {
//            dockerClient.execStartCmd(execCreateCmdResponse1.getId()).exec(
//                    new ExecStartResultCallback(stdout, stderr)).awaitCompletion();
//            System.out.println(stdout);
//            System.out.println(stderr);
//        }
//        catch(InterruptedException e) {
//
//        }
        return stdout.toString();

    }

    @Override
    public void deleteContainer() {

    }

    private File getSSHKeyFile(){
        return null;
    }


}