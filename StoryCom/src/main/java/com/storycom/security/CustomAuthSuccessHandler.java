package com.storycom.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.debug("Authentication successful.");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StoryUser storyUser = (StoryUser) auth.getPrincipal();

        SavedRequest savedRequest = (SavedRequest) request.getSession(false).getAttribute("SPRING_SECURITY_SAVED_REQUEST");

        String redirectUrl = request.getContextPath() + "/";

        request.getSession(false).setAttribute("user", "(" + storyUser.getFullName() + ") " + storyUser.getUsername());

        if (savedRequest != null) {
            redirectUrl = savedRequest.getRedirectUrl();
        }

        log.debug("Redirecting to page: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
