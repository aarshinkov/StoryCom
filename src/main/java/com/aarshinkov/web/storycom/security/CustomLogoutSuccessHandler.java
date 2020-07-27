package com.aarshinkov.web.storycom.security;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler
{
  @Autowired
  private MessageSource messageSource;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
  {
//    String locale = systemService.getLocale(request);

//    request.getSession().setAttribute("msgInfo", messageSource.getMessage("success.msg.logout", null, new Locale(locale)));
    response.sendRedirect(request.getContextPath() + "/login");

    super.onLogoutSuccess(request, response, authentication);
  }
}
