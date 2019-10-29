package com.safb.storycom.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.*;

public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException
  {

    log.debug("Authentication failed: " + ex.getMessage());
    String username = request.getParameter("username");
    log.debug("Username: " + username);

    setDefaultFailureUrl("/login?error");

    super.onAuthenticationFailure(request, response, ex);
  }

}
