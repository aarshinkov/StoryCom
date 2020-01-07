package com.safb.storycom.services;

import com.safb.storycom.domain.Password;
import com.safb.storycom.domain.RegisterUser;
import com.safb.storycom.entity.UserEntity;
import org.springframework.security.core.userdetails.*;

public interface UserService extends UserDetailsService
{
  UserEntity registerUserToUser(RegisterUser registerUser);

  void registerUser(UserEntity user);

  void changePassword(UserEntity loggedUser, Password password);
}
