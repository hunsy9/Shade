package com.oslab.agent.service.user;


import com.oslab.agent.model.transfer.userDto.SignInReqDto;
import com.oslab.agent.model.transfer.userDto.SignupReqDto;
import com.oslab.agent.model.transfer.userDto.User;
import com.oslab.agent.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    public User lookup(Integer userId) throws SQLException{
        return userRepository.findUserById(userId);
    }
    public boolean signUp(SignupReqDto signupReqDto) throws SQLException{
        return userRepository.signUp(signupReqDto);
    }

    public boolean signIn(SignInReqDto signinReqDto) throws SQLException{
        return userRepository.signIn(signinReqDto);
    }

    public boolean checkEmailDuplication(String email) throws SQLException {
        return userRepository.checkEmailDuplication(email);
    }

    public boolean checkAdmin(String email) throws SQLException{
        return userRepository.checkAdmin(email);
    }

}
