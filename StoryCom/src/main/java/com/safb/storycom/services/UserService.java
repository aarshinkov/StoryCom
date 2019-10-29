package com.safb.storycom.services;

import com.safb.storycom.domain.Password;
import com.safb.storycom.domain.RegisterUser;
import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.security.LoggedUser;

public interface UserService
{
  UserEntity registerUserToUser(RegisterUser registerUser);

  void registerUser(UserEntity user);

  void changePassword(LoggedUser loggedUser, Password password);
}
