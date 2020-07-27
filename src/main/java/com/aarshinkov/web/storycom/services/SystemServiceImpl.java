package com.aarshinkov.web.storycom.services;

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
}
