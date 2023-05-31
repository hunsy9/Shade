package com.oslab.agent.repository.mapper;

import com.oslab.agent.model.transfer.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserById(Long userId);
}
