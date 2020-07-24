package com.aarshinkov.web.storycom.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorDefaultController implements org.springframework.boot.web.servlet.error.ErrorController
{
  @GetMapping(value = "/error")
  public String handleError(HttpServletRequest request)
  {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null)
    {
      int statusCode = Integer.parseInt(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value())
      {
        return "redirect:/404";
      }
    }

    return "redirect:/500";
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
