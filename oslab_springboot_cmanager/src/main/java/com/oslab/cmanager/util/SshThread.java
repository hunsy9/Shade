package com.oslab.cmanager.util;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.oslab.cmanager.configuration.websocket.entity.SshConnectionRoom;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Data
@RequiredArgsConstructor
public class SshThread extends Thread {

    private BlockingQueue<String> commandQueue;
    private SimpMessageSendingOperations sendingOperations;
    private boolean exitRequested;
    private String command;

    private Session sshSession;
    private ChannelShell sshChannelShell;
    private String key;
    private String password;
    private SshUtil sshUtil;
    private InputThread inputThread;
    private OutputThread outputThread;
    private SshConnectionRoom sshConnectionRoom;
    private Long org_id;
    private Long server_id;
    private Boolean keyExistence;

    public SshThread(String key, String password, String initCommand, SshUtil sshUtil, SshConnectionRoom sshConnectionRoom, SimpMessageSendingOperations sendingOperations, Long org_id, Long server_id, Boolean keyExistence) {
        System.out.println("ssh Thread generated.");
        this.command = initCommand;
        this.sshUtil = sshUtil;
        this.key = key;
        this.password = password;
        this.sshConnectionRoom = sshConnectionRoom;
        this.sendingOperations = sendingOperations;
        this.exitRequested = false;
        this.commandQueue = new LinkedBlockingQueue<>();
        this.org_id = org_id;
        this.server_id = server_id;
        this.keyExistence = keyExistence;
    }

    public String requestCommand() {
        try {
            commandQueue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Boolean terminateThread(){
        this.exitRequested = true;
        inputThread.closeInputThread();
        outputThread.closeOutputThread();
        return this.exitRequested;
    }

    public String findSSHKey(Long org_id, Long server_id){
        String DATA_DIRECTORY = "/home/opc/oidc/key/"+org_id+"/"+server_id;
        log.info("findSSHKEY: " + DATA_DIRECTORY);
        File dir = new File(DATA_DIRECTORY);
        File files[] = dir.listFiles();
        log.info("files: " + files.toString());
        File targetKey = Arrays.stream(files).toList().get(0);
        return targetKey.toString();
    }

    @Override
    public void run() {
        while(!exitRequested){
            try {
                // SSH 연결 설정
                JSch jsch = new JSch();
                if(keyExistence){
                    String publicKey = findSSHKey(org_id, server_id);
                    jsch.addIdentity(publicKey);
                }
                List<String> info = Arrays.stream(key.split(":")).toList();
                Session session = jsch.getSession(info.get(0), info.get(1), Integer.parseInt(info.get(2)));
                session.setPassword(password);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                sshUtil.setKeyToSessionMap(key, session);

                log.info("채널 생성 후 연결");

                // 입력 받기 위한 준비
                ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
                channelShell.setPty(true);

                channelShell.connect();

                sshUtil.setKeyToChannelMap(key, channelShell);
                sshChannelShell = channelShell;

                // 입력 및 결과 수신 스레드 생성 및 시작
                InputThread inputThread = new InputThread(channelShell);
                OutputThread outputThread = new OutputThread(channelShell, sendingOperations, sshConnectionRoom );
                this.inputThread = inputThread;
                this.outputThread = outputThread;
                inputThread.start();
                outputThread.start();
                commandQueue.put("");
   

                // 입력 받기
                while (true) {
                    if (exitRequested) {
                        // "exit" 입력 시 스레드 종료
                        channelShell.getOutputStream().write("exit\n".getBytes());
                        channelShell.getOutputStream().flush();
                        break;
                    }

                    String command = commandQueue.poll();
                    if (command != null) {
                        // 외부에서 명령어 전달받으면 InputThread로 전송
                        inputThread.sendCommand(command);
                    }

                    Thread.sleep(100);  // 작업 흐름을 조절하기 위한 대기
                }

                // 입력 및 결과 수신 스레드가 종료될 때까지 대기
                inputThread.join();
                outputThread.join();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("sshThread has been Stopped.");
        }
        }

    private static class InputThread extends Thread {
        private ChannelShell channelShell;
        private BlockingQueue<String> commandQueue;

        private boolean exitRequest;

        public InputThread(ChannelShell channelShell) {
            this.channelShell = channelShell;
            this.exitRequest = false;
            this.commandQueue = new LinkedBlockingQueue<>();
        }

        public void sendCommand(String command) {
            try {
                commandQueue.put(command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void closeInputThread(){
            exitRequest = !exitRequest;
        }

        @Override
        public void run() {
            while(!exitRequest){
                try {
                    while (true) {
                        if (exitRequest) {
                            break;
                        }

                        String command = commandQueue.poll();
                        if (command != null) {
                            // 외부에서 명령어 전달받으면 ChannelShell로 전송
                            log.info("char: "+ command);
                            OutputStream outputStream = channelShell.getOutputStream();
                            PrintStream commander = new PrintStream(outputStream,true);
                            commander.write(command.getBytes(StandardCharsets.UTF_8));
                        }
                        Thread.sleep(100);
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("sshInputThread has been Stopped.");

        }
    }

    @RequiredArgsConstructor
    private static class OutputThread extends Thread {
        private ChannelShell channelShell;
        private SimpMessageSendingOperations sendingOperations;
        private String sendingUrl;

        private boolean exitRequest;


        public OutputThread(ChannelShell channelShell, SimpMessageSendingOperations sendingOperations, SshConnectionRoom sshConnectionRoom) {
            int user_id = sshConnectionRoom.getUser_id();
            int server_id = sshConnectionRoom.getServer_id();
            String room_id = sshConnectionRoom.getRoom_id();
            this.channelShell = channelShell;
            this.sendingOperations = sendingOperations;
            this.exitRequest = false;
            this.sendingUrl = "/sub/ws/" + user_id + "/" + server_id + "/" + room_id;
        }

        public void closeOutputThread(){
            exitRequest = !exitRequest;
        }

        @Override
        public void run() {
            while(!exitRequest){
                try (InputStream inputStream = channelShell.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    String line;
                    while (!exitRequest) {
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            sendingOperations.convertAndSend(sendingUrl, line);
                        }
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("sshOutputThread has been Stopped.");

        }
    }
}

