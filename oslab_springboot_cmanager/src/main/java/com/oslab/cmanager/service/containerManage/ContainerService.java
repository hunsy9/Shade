package com.oslab.cmanager.service.containerManage;

import java.io.File;

public interface ContainerService {
    public void createContainer();
    //public void startContainer();
    public void deleteContainer();

    private File getSSHKeyFile() {
        return null;
    }



}
