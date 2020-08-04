package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.domain.*;
import com.aarshinkov.web.storycom.dto.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;

@Service
public class SystemServiceImpl implements SystemService
{
  @Override
  public Object getSessionAttribute(HttpServletRequest request, String attributeName)
  {
    HttpSession session = request.getSession();
    return session.getAttribute(attributeName);
  }

  @Override
  public void changeLoggerUserInfo(HttpServletRequest request, UserDto user)
  {
    HttpSession session = request.getSession();

    NameDomain names = NameDomain.builder().firstName(user.getFirstName()).lastName(user.getLastName()).build();

    session.setAttribute("user", names);
    session.setAttribute("email", user.getEmail());
  }
}
