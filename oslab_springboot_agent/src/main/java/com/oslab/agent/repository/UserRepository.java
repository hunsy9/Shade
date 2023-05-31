package com.oslab.agent.repository;


import com.oslab.agent.model.transfer.User;
import com.oslab.agent.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;
    public User findUserById(Long userId){
        return userMapper.findUserById(userId);
    }
}
