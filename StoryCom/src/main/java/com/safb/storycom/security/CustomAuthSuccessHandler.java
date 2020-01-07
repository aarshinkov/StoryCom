package com.safb.storycom.security;

import com.safb.storycom.dto.*;
import com.safb.storycom.entity.*;
import com.safb.storycom.utils.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.savedrequest.*;

public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException
  {
    log.debug("Authentication successful.");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity userEntity = (UserEntity) auth.getPrincipal();

    UserDto userDto = new UserDto();

    SavedRequest savedRequest = (SavedRequest) request.getSession(false).getAttribute("SPRING_SECURITY_SAVED_REQUEST");

    String redirectUrl = request.getContextPath() + "/";

    HttpSession session = request.getSession(false);

    session.setAttribute(SessionAttributes.LOADED_USER, userDto.getUserId());
    session.setAttribute("user", userDto.getFirstName() + " " + userDto.getLastName());

    if (savedRequest != null)
    {
      redirectUrl = savedRequest.getRedirectUrl();
    }

    log.debug("Redirecting to page: " + redirectUrl);
    response.sendRedirect(redirectUrl);
  }
}
