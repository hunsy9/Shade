package com.oslab.cmanager.util;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.oslab.cmanager.model.transfer.SSHDto.StartingInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private volatile static boolean exitRequested;
    private String command;

    private Session sshSession;
    private ChannelShell sshChannelShell;
    private String key;

    private String password;
    private SshUtil sshUtil;

    public SshThread(String key, String password, String initCommand, SshUtil sshUtil) {

        System.out.println("ssh Thread generated.");
        this.command = initCommand;
        this.sshUtil = sshUtil;
        this.key = key;
        this.password = password;
        this.commandQueue = new LinkedBlockingQueue<>();
    }

    public String requestCommand() {
        log.info("현재 명령어: {}",command);

        try {
            commandQueue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void run() {
            try {
                // SSH 연결 설정
                JSch jsch = new JSch();
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
                OutputThread outputThread = new OutputThread(channelShell);
                inputThread.start();
                outputThread.start();

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
        }

    private static class InputThread extends Thread {
        private ChannelShell channelShell;
        private BlockingQueue<String> commandQueue;

        public InputThread(ChannelShell channelShell) {
            this.channelShell = channelShell;
            this.commandQueue = new LinkedBlockingQueue<>();
        }

        public void sendCommand(String command) {
            try {
                commandQueue.put(command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (exitRequested) {
                        break;
                    }

                    String command = commandQueue.poll();
                    if (command != null) {
                        // 외부에서 명령어 전달받으면 ChannelShell로 전송
                        OutputStream outputStream = channelShell.getOutputStream();
                        PrintStream commander = new PrintStream(outputStream,true);
                        commander.println(command);
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class OutputThread extends Thread {
        private ChannelShell channelShell;

        public OutputThread(ChannelShell channelShell) {
            this.channelShell = channelShell;
        }

        @Override
        public void run() {
            try (InputStream inputStream = channelShell.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while (!exitRequested) {
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
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
    }
}

