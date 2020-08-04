package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.dto.*;
import javax.servlet.http.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface SystemService
{
  Object getSessionAttribute(HttpServletRequest request, String attributeName);

  void changeLoggerUserInfo(HttpServletRequest request, UserDto user);
}
