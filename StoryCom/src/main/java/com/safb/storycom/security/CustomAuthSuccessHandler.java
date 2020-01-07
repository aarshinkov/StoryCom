package com.safb.storycom.security;

import com.safb.storycom.entity.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.*;

public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException
  {
    log.debug("Authentication successful.");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggedUser = (UserEntity) auth.getPrincipal();

    SavedRequest savedRequest = (SavedRequest) request.getSession(false).getAttribute("SPRING_SECURITY_SAVED_REQUEST");

    String redirectUrl = request.getContextPath() + "/";

    request.getSession(false).setAttribute("user", "(" + loggedUser.getFullName() + ") " + loggedUser.getUsername());

    if (savedRequest != null)
    {
      redirectUrl = savedRequest.getRedirectUrl();
    }

    log.debug("Redirecting to page: " + redirectUrl);
    response.sendRedirect(redirectUrl);
  }
}
