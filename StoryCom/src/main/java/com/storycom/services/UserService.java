package com.storycom.services;

import com.storycom.domain.RegisterUser;
import com.storycom.entity.User;

public interface UserService {
    User registerUserToUser(RegisterUser registerUser);
    void registerUser(User user);
}
