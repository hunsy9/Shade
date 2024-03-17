package com.oslab.agent.controller.sshRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import com.oslab.agent.model.transfer.requestDto.*;
import com.oslab.agent.service.sshRequest.RequestService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/api/request/getRoomId/{user_id}/{server_id}")
    public String getWebSocketUrl(@PathVariable int user_id, @PathVariable int server_id) {
        return requestService.getWebSocketUrl(user_id, server_id);
    }

    @PostMapping("api/request/generateKey")
    public ResponseEntity<KeyBundle> generateKey(@RequestBody ConnectingDto connectingDto) {
        return ResponseEntity.ok().body(requestService.generateKey(connectingDto));
    }

    @PostMapping("/api/request/connect")
    public void connectRoom(@RequestBody KeyBundle keyBundle) {
        requestService.connectRoom(keyBundle);
    }

    @PostMapping("/api/request/command")
    public ResponseEntity<Boolean> command(@RequestBody CommandDto commandDto) {
        return requestService.command(commandDto);
    }

    @PostMapping("/api/request/exitShell")
    public ResponseEntity<Boolean> exitShell(@RequestBody ExitDto exitDto) {
        return requestService.exitShell(exitDto);
    }

    @PostMapping("api/request/test")
    public ResponseEntity<?> sshConnectionTest(@RequestPart(value = "keyfile", required = false) MultipartFile keyfile, @RequestPart("server") ConTestDto server) throws IOException {
        return requestService.sshConnectionTest(keyfile, server);
    }

    @PostMapping("api/request/startPty")
    public void startPty(@RequestBody PtyConnectionDto ptyConnectionDto) throws IOException {
        System.out.println(ptyConnectionDto.toString());
        requestService.startPty(ptyConnectionDto);
    }
}
