package com.oslab.agent.repository.user.mapper;

import com.oslab.agent.model.transfer.userDto.SignInReqDto;
import com.oslab.agent.model.transfer.userDto.SignupReqDto;
import com.oslab.agent.model.transfer.userDto.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface UserMapper {
    User findUserById(Integer userId) throws SQLException;

    Boolean signUp(SignupReqDto signupReqDto) throws SQLException;

    Integer signIn(SignInReqDto signInReqDto) throws SQLException;

    Integer checkEmailDuplication(String email) throws SQLException;

    Integer checkAdmin(String email) throws SQLException;
}
