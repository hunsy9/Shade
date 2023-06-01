package com.oslab.agent.service.user;

import com.oslab.agent.controller.user.dto.SignInReqDto;
import com.oslab.agent.controller.user.dto.SignupReqDto;
import com.oslab.agent.model.transfer.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserServiceImpl {
    public User lookup(Integer userId) throws SQLException;

    public boolean signUp(SignupReqDto signupReqDto) throws SQLException;

    public boolean signIn(SignInReqDto signInReqDto) throws SQLException;

    public boolean checkEmailDuplication(String email) throws SQLException;
}
