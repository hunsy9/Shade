package com.oslab.agent.controller;


import com.oslab.agent.model.transfer.User;
import com.oslab.agent.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/lookup/{userId}")
    public User lookup(@PathVariable Long userId){
        return userService.lookup(userId);
    }

}
