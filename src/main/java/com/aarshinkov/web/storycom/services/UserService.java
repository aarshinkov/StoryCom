package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.models.auth.*;
import org.springframework.security.core.userdetails.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface UserService extends UserDetailsService
{
  UserDto createUser(SignupModel signup);
}
