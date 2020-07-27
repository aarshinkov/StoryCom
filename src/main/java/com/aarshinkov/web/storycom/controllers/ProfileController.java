package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.repositories.*;
import com.aarshinkov.web.storycom.services.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Controller
public class ProfileController
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private SystemService sessionService;

  @GetMapping(value = "/profile")
  public String profile(HttpServletRequest request, Model model)
  {
    long userId = (Long) sessionService.getSessionAttribute(request, "userId");
    UserEntity user = usersRepository.findByUserId(userId);
    
    model.addAttribute("user", user);
    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "profile");

    return "profile/profile";
  }
}
