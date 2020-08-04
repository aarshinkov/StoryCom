package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.models.auth.*;
import com.aarshinkov.web.storycom.models.users.*;
import org.springframework.security.core.userdetails.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface UserService extends UserDetailsService
{
  UserDto getUserByUserId(Long userId);

  UserDto createUser(SignupModel signup);

  UserDto updateUser(UserEditModel uem);

  UserDto changePassword(ChangePasswordModel cpm);

  boolean isPasswordMatch(Long userId, String password);
}
