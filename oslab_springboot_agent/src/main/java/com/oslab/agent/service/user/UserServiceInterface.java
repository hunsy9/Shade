package com.oslab.agent.service.user;

import com.oslab.agent.model.transfer.userDto.SignInReqDto;
import com.oslab.agent.model.transfer.userDto.SignupReqDto;
import com.oslab.agent.model.transfer.userDto.User;

import java.sql.SQLException;

public interface UserServiceInterface {
    User lookup(Integer userId) throws SQLException;

    boolean signUp(SignupReqDto signupReqDto) throws SQLException;

    boolean signIn(SignInReqDto signInReqDto) throws SQLException;

    boolean checkEmailDuplication(String email) throws SQLException;
}
