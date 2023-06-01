package com.oslab.agent.repository.user;


import com.oslab.agent.controller.user.dto.SignInReqDto;
import com.oslab.agent.controller.user.dto.SignupReqDto;
import com.oslab.agent.model.transfer.User;
import com.oslab.agent.repository.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;
    public User findUserById(Integer userId) throws SQLException {
        return userMapper.findUserById(userId);
    }
    public boolean signUp(SignupReqDto signupReqDto) throws SQLException{
        log.info(signupReqDto.getUser_email());
        log.info(signupReqDto.getUser_name());
        return userMapper.signUp(signupReqDto);
    }
    public boolean signIn(SignInReqDto signInReqDto) throws SQLException{
        return userMapper.signIn(signInReqDto) > 0;
    }

    public boolean checkEmailDuplication(String email) throws SQLException{
        return userMapper.checkEmailDuplication(email) > 0;
    }
}
