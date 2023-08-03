package com.oslab.agent.repository.user.mapper;

import com.oslab.agent.model.transfer.userDto.SignInReqDto;
import com.oslab.agent.model.transfer.userDto.SignInResDto;
import com.oslab.agent.model.transfer.userDto.SignupReqDto;
import com.oslab.agent.model.transfer.userDto.UserOrgs;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface UserMapper {
    List<UserOrgs> getUserOrgInfo(Integer userId) throws SQLException;

    Boolean signUp(SignupReqDto signupReqDto) throws SQLException;

    SignInResDto signIn(SignInReqDto signInReqDto) throws SQLException;

    Integer checkEmailDuplication(String email) throws SQLException;

    Integer checkAdmin(String email) throws SQLException;
}
