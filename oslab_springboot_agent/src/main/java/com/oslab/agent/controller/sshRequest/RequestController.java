package com.oslab.agent.controller.sshRequest;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import com.oslab.agent.model.transfer.requestDto.CommandDto;
import com.oslab.agent.model.transfer.requestDto.ConnectingDto;
import com.oslab.agent.model.transfer.requestDto.KeyBundle;
import com.oslab.agent.service.sshRequest.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RequestController {
    private final RequestService requestService;
    @GetMapping("/api/request/getRoomId/{user_id}/{server_id}")
    public String getWebSocketUrl(@PathVariable int user_id, @PathVariable int server_id){
        return requestService.getWebSocketUrl(user_id, server_id);
    }

    @PostMapping("api/request/generateKey")
    public ResponseEntity<KeyBundle> generateKey(@RequestBody ConnectingDto connectingDto){
        return requestService.generateKey(connectingDto);
    }

    @PostMapping("/api/request/connect")
    public void connectRoom(@RequestBody KeyBundle keyBundle){
        requestService.connectRoom(keyBundle);
    }

    @PostMapping("/api/request/command")
    public ResponseEntity<Boolean> command(@RequestBody CommandDto commandDto){
        return requestService.command(commandDto);
    }
}
