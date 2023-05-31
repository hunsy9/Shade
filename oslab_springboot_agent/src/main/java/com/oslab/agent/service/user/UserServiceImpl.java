package com.oslab.agent.service.user;

import com.oslab.agent.model.transfer.User;

public interface UserServiceImpl {
    public User lookup(Long userId);
}
