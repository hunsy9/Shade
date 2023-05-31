package com.oslab.cmanager.controller;

import com.oslab.cmanager.service.containerManage.ContainerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContainerController {

    private final ContainerServiceImpl containerService;

    public void createContainer(){
        containerService.createContainer();
    }

    public void deleteContainer(){
        containerService.deleteContainer();
    }

}
