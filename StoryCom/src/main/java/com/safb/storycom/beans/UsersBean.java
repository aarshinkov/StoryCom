package com.safb.storycom.beans;

import com.safb.storycom.base.Base;
import com.safb.storycom.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsersBean extends Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  private UserEntity loggedUser;

  public UserEntity getPrincipal()
  {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return (UserEntity) auth.getPrincipal();
  }
}
