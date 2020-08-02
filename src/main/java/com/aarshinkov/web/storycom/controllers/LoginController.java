package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.base.*;
import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.models.auth.*;
import com.aarshinkov.web.storycom.services.*;
import javax.validation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Controller
public class LoginController extends Base
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @GetMapping(value = "/login")
  public String prepareLogin(Model model)
  {
    model.addAttribute("globalMenu", "login");

    return "auth/login";
  }

  @GetMapping(value = "/signup")
  public String prepareSignup(Model model)
  {
    SignupModel signup = new SignupModel();

    model.addAttribute("signup", signup);
    model.addAttribute("globalMenu", "signup");

    return "auth/signup";
  }

  @PostMapping(value = "/signup")
  public String signup(@ModelAttribute("signup") @Valid SignupModel signup,
          BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
  {
    if (!signup.getPassword().equals(signup.getConfirmPassword()))
    {
      bindingResult.rejectValue("password", "errors.password.nomatch");
      bindingResult.rejectValue("confirmPassword", "errors.password.nomatch");
    }

    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", "signup");
      return "auth/signup";
    }

    try
    {
      UserDto createdUser = userService.createUser(signup);
      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("signup.success", createdUser.getFullName()));
    }
    catch (Exception e)
    {
      redirectAttributes.addFlashAttribute("msgError", getMessage("signup.error"));
    }

    return "redirect:/login";
  }
}
