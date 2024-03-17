package com.oslab.agent.util;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.oslab.agent.model.transfer.enums.TestResult;
import com.oslab.agent.model.transfer.serverDto.ConnectionTestDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SshTestThread extends Thread {

    private TestResult testSuccess = TestResult.INIT;

    private final ConnectionTestDto connectionTestDto;

    public SshTestThread(ConnectionTestDto connectionTestDto) {
        this.connectionTestDto = connectionTestDto;
    }

    @Override
    public void run() {
        testSuccess = TestResult.SUCCESS;

//            try {
//                // SSH 연결 설정
//                JSch jsch = new JSch();
//                if(connectionTestDto.getFileDir()!=null){
//                    String publicKey = connectionTestDto.getFileDir();
//                    jsch.addIdentity(publicKey);
//                }
//                Session session = jsch.getSession(connectionTestDto.getUsername(), connectionTestDto.getHost(), connectionTestDto.getPort());
//                String pwd = connectionTestDto.getPassword();
//                if(pwd!=null){
//                    session.setPassword(pwd);
//                }
//                session.setConfig("StrictHostKeyChecking", "no");
//                session.connect();
//
//                log.info("채널 생성 후 세션 연결");
//
//                // 입력 받기 위한 준비
//                ChannelExec channel = (ChannelExec) session.openChannel("exec");
//                channel.setPty(true);
//
//                channel.connect();
//
//                if(channel.isConnected()){
//                    testSuccess = TestResult.SUCCESS;
//                    log.info("Connection Test Success!");
//                }
//
//            } catch (Exception e) {
//                testSuccess = TestResult.CONNECTIONERR;
//                log.info("Connection Test Failed!");
//            }
    }

}

