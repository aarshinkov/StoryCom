package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.base.*;
import com.aarshinkov.web.storycom.collections.*;
import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.services.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Controller
public class UsersController extends Base
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @GetMapping(value = "/users")
  public String getUsers(@RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
          @RequestParam(name = "limit", defaultValue = "5", required = false) Integer limit, Model model)
  {
    ObjCollection<UserDto> users = userService.getUsers(page, limit);

    model.addAttribute("users", users.getCollection());

    model.addAttribute("pageWrapper", users.getPage());
    model.addAttribute("maxPagesPerView", 5);

    model.addAttribute("globalMenu", "users");

    return "users/users";
  }

  @PostMapping(value = "/user/delete")
  public String deleteUser(@RequestParam(name = "userId", required = true) Long userId,
          RedirectAttributes redirectAttributes)
  {
    LOG.debug("userId: " + userId);

    try
    {
      UserDto deletedUser = userService.deleteUser(userId);
      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("users.delete.success"));
    }
    catch (Exception e)
    {
      LOG.debug("Error deleting user", e);
      redirectAttributes.addFlashAttribute("msgError", getMessage("users.delete.error"));
    }

    return "redirect:/users";
  }
}
