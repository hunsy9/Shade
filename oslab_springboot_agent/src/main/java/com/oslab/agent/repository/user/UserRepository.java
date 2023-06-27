package com.oslab.agent.repository.user;


import com.oslab.agent.model.transfer.userDto.SignInReqDto;
import com.oslab.agent.model.transfer.userDto.SignupReqDto;
import com.oslab.agent.model.transfer.userDto.UserOrgs;
import com.oslab.agent.repository.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;
    public List<UserOrgs> getUserOrgInfo(Integer userId) throws SQLException {
        return userMapper.getUserOrgInfo(userId);
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

    public boolean checkAdmin(String email) throws SQLException{
        return userMapper.checkAdmin(email) > 0;
    }
}
