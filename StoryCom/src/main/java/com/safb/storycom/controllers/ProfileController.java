package com.safb.storycom.controllers;

import com.safb.storycom.base.*;
import com.safb.storycom.entity.*;
import com.safb.storycom.services.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController extends Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String GLOBAL_MENU = "profile";

  @Autowired
  private ProfileService profileService;

  @Autowired
  private SystemService systemService;

  @Autowired
  private UserService userService;

  @Autowired
  private HttpSession session;

  @GetMapping
  public String viewProfile(Model model)
  {
    model.addAttribute("user", getUser());

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "personal");

    return "profile/personal";
  }

  @PostMapping
  public String saveProfile(UserEntity user, Model model)
  {
    user.setUserId(getLoggedUser().getUserId());

    profileService.savePersonalInfo(user);

    return "redirect:/profile";
  }

  @GetMapping(value = "/details")
  public String viewDetails(Model model)
  {
    Integer userId = getLoggedUserId();
    UserEntity user = userService.getUserByUserId(userId);
    model.addAttribute("user", user);
    model.addAttribute("countries", systemService.getAllCountries(session));

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "details");

    return "profile/details";
  }

  @PostMapping(value = "/details")
  public String saveDetails(UserEntity user, Model model)
  {
    user.setUserId(getLoggedUser().getUserId());

    profileService.saveDetails(user);

    return "redirect:/profile/details";
  }

//  @GetMapping(value = "/{username}")
//  public String myStories(@PathVariable("username") String username, Model model)
//  {
//    boolean isMe = true;
//    String currentUsername = getLoggedUser().getUsername();
//
//    log.debug("username: " + currentUsername);
//
//    // Ako lognatiq potrebitel e razlichen ot tozi koito biva dostapvan
//    if (!currentUsername.equals(username))
//    {
//      isMe = false;
//      model.addAttribute("isMe", isMe);
//    }
//
//    User user = usersRepository.findUserByUsername(username);
//
//    if (user == null)
//    {
//      log.error("No user with username " + username + " has been found");
//      return "profile/noUserFound";
//    }
//
//    log.debug("userId: " + user.getUserId()); 
//
//    List<Story> stories = storiesRepository.findAllByUserOrderByStoryIdDesc(user);
//
//    model.addAttribute("stories", stories);
//
//    model.addAttribute("isMe", isMe);
//    model.addAttribute("currUserId", getLoggedUser().getUserId());
//    model.addAttribute("user", user);
//
//    model.addAttribute("globalMenu", GLOBAL_MENU);
//    model.addAttribute("submenu", "public");
//    return "profile/public";
//  }
}
