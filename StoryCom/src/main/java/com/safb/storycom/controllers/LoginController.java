package com.safb.storycom.controllers;

import com.safb.storycom.base.Base;
import com.safb.storycom.domain.RegisterUser;
import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import org.slf4j.*;

@Controller
public class LoginController extends Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @GetMapping(value = "/login")
  public String prepareLogin(Model model)
  {
    model.addAttribute("globalMenu", "login");

    return "login/login";
  }

  @GetMapping(value = "/signup")
  public String prepareSignup(Model model)
  {

    RegisterUser registerUser = new RegisterUser();

    model.addAttribute("regUser", registerUser);
    model.addAttribute("globalMenu", "signup");

    return "login/signup";
  }

  @PostMapping(value = "/signup")
  public String signup(@ModelAttribute("regUser") @Valid RegisterUser regUser, BindingResult bindingResult, Model model)
  {

    log.debug("RegisterUser: " + regUser.toString());

    if (bindingResult.hasErrors())
    {
      return "login/signup";
    }

    UserEntity user = userService.registerUserToUser(regUser);

    log.debug("User: " + user.toString());

    userService.registerUser(user);

    return "redirect:/login";
  }
}
