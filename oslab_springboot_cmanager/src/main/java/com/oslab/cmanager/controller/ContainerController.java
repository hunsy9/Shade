package com.oslab.cmanager.controller;

import com.oslab.cmanager.service.containerManage.ContainerServiceImpl;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContainerController {

    private final ContainerServiceImpl containerService;

    /*PostMapping("api/container/create")*/
    @GetMapping("api/container/create")
    public void createContainer(/*@RequestBody*/) { containerService.createContainer(); }

    @GetMapping("api/container/start")
//    public void startContainer(/*@PathVariable String container_name*/) { containerService.startContainer(/*container_name*/); }

    public void deleteContainer(){
        containerService.deleteContainer();
    }

}
