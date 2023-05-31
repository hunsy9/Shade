package com.oslab.cmanager.service.containerManage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class ContainerServiceImpl implements ContainerService{
    @Override
    public void createContainer() {

    }
    @Override
    public void deleteContainer() {

    }

    private File getSSHKeyFile(){
        return null;
    }
}
