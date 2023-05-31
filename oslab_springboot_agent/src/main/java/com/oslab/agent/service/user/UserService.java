package com.oslab.agent.service.user;


import com.oslab.agent.model.transfer.User;
import com.oslab.agent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl{

    private final UserRepository userRepository;

    public User lookup(Long userId){
        return userRepository.findUserById(userId);
    }
}
