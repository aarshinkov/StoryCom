package com.storycom.services;

import com.storycom.domain.Password;
import com.storycom.domain.RegisterUser;
import com.storycom.entity.User;
import com.storycom.security.StoryUser;

public interface UserService {
    User registerUserToUser(RegisterUser registerUser);
    void registerUser(User user);
    void changePassword(StoryUser storyUser, Password password);
}
