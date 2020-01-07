package com.safb.storycom.services;

import com.safb.storycom.domain.*;
import com.safb.storycom.entity.*;
import org.springframework.security.core.userdetails.*;

public interface UserService extends UserDetailsService
{
  UserEntity getUserByUserId(Integer userId);

  UserEntity registerUserToUser(RegisterUser registerUser);

  void registerUser(UserEntity user);

  void changePassword(UserEntity loggedUser, Password password);
}
