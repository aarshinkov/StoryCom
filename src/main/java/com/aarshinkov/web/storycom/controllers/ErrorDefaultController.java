package com.aarshinkov.web.storycom.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.*;
import org.springframework.security.web.savedrequest.*;

@Controller
public class ErrorDefaultController implements ErrorController
{
  private final RequestCache requestCache = new HttpSessionRequestCache();
  
  @GetMapping(value = "/error")
  public String handleError(HttpServletRequest request)
  {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null)
    {
      int statusCode = Integer.parseInt(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value())
      {
        return "errors/404";
//        return "redirect:/404";
      }
      if (statusCode == HttpStatus.FORBIDDEN.value())
      {
        
        return "errors/403";
//        return "redirect:/403";
      }
    }

    return "errors/500";
//    return "redirect:/500";
  }

  @GetMapping(value = "/403")
  public String error403()
  {
    return "errors/403";
  }

  @GetMapping(value = "/404")
  public String error404()
  {
    return "errors/404";
  }

  @GetMapping(value = "/500")
  public String error500()
  {
    return "errors/500";
  }

  @Override
  public String getErrorPath()
  {
    return "/error";
  }
}
