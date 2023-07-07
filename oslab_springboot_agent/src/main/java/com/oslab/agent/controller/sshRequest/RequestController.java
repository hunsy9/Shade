package com.oslab.agent.controller.sshRequest;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import com.oslab.agent.model.transfer.requestDto.ConnectingDto;
import com.oslab.agent.service.sshRequest.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    @GetMapping("/api/request/getRoomId/{user_id}/{server_id}")
    public String getWebSocketUrl(@PathVariable int user_id, @PathVariable int server_id){
        return requestService.getWebSocketUrl(user_id, server_id);
    }

    @PostMapping("/api/request/connect")
    public void connectRoom(@RequestBody ConnectingDto connectingDto){
        requestService.connectRoom(connectingDto);
    }
}
