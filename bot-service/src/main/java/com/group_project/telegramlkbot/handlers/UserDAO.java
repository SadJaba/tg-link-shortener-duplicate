package com.group_project.telegramlkbot.handlers;

import com.group_project.telegramlkbot.data.UserEntity;
import com.group_project.telegramlkbot.data.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserEntity user) {
        userRepository.save(user);
    }
}
