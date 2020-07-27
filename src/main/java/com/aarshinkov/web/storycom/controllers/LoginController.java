package com.aarshinkov.web.storycom.controllers;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Controller
public class LoginController
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @GetMapping(value = "/login")
  public String prepareLogin(Model model)
  {
    model.addAttribute("globalMenu", "login");

    return "auth/login";
  }

  @GetMapping(value = "/signup")
  public String prepareSignup(Model model)
  {
    model.addAttribute("globalMenu", "signup");

    return "auth/signup";
  }

  @PostMapping(value = "/signup")
  public String signup(Model model)
  {
    model.addAttribute("globalMenu", "signup");

    return "redirect:/signup";
  }
}
