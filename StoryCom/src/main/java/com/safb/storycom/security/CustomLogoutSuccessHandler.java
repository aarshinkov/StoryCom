package com.safb.storycom.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.*;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  public CustomLogoutSuccessHandler(String defaultTargetURL)
  {
    this.setDefaultTargetUrl(defaultTargetURL);
  }

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
  {
    super.onLogoutSuccess(request, response, authentication);
  }
}
