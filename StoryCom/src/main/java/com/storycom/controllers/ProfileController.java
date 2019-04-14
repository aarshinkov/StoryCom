package com.storycom.controllers;

import com.storycom.base.*;
import com.storycom.entity.*;
import com.storycom.repository.*;
import java.sql.*;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
public class ProfileController extends Base
{

  private static final String GLOBAL_MENU = "profile";

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private CountriesRepository countriesRepository;

  @Autowired
  private StoriesRepository storiesRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping
  public String viewProfile(Model model)
  {

    User user = getUser();

    log.debug(user.toString());

    model.addAttribute("user", user);

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "personal");

    return "profile/personal";
  }

  @PostMapping
  public String saveProfile(User user, Model model)
  {

    log.debug("Saving user profile...");

    user.setUserId(getStoryUser().getUserId());

    log.debug("User id: " + user.getUserId());

    try
    {
      CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.UPDATE_USER_INFORMATION(?,?,?,?)}");
      cstmt.setInt(1, user.getUserId());
      cstmt.setString(2, user.getFirstName());
      cstmt.setString(3, user.getLastName());
      cstmt.setString(4, user.getEmail());

      cstmt.execute();
      log.debug("User profile saved successfully!");
    }
    catch (Exception e)
    {
      log.error("Error saving user profile!", e);
    }

    return "redirect:/personal";
  }

  @GetMapping(value = "/details")
  public String viewDetails(Model model)
  {

    User user = getUser();

    model.addAttribute("user", user);
    model.addAttribute("countries", countriesRepository.findAll());

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "details");

    return "profile/details";
  }

  @PostMapping(value = "/details")
  public String saveDetails(User user, Model model)
  {
    log.debug("Saving user details...");

    user.setUserId(getStoryUser().getUserId());

    try
    {
      CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.UPDATE_USER_DETAILS(?,?,?,?,?,?,?)}");
      cstmt.setInt(1, user.getUserId());
      cstmt.setString(2, user.getUserDetail().getGender());
      cstmt.setString(3, user.getUserDetail().getCountry().getCountryName());
      cstmt.setString(4, user.getUserDetail().getFacebook());
      cstmt.setString(5, user.getUserDetail().getTwitter());
      cstmt.setString(6, user.getUserDetail().getYoutube());
      cstmt.setString(7, user.getUserDetail().getInstagram());

      cstmt.execute();
      log.debug("User details saved successfully!");
    }
    catch (Exception e)
    {
      log.error("Error saving user details!", e);
    }

    return "redirect:/profile/details";
  }

//  @GetMapping(value = "/{username}")
//  public String myStories(@PathVariable("username") String username, Model model)
//  {
//    boolean isMe = true;
//    String currentUsername = getStoryUser().getUsername();
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
//    model.addAttribute("currUserId", getStoryUser().getUserId());
//    model.addAttribute("user", user);
//
//    model.addAttribute("globalMenu", GLOBAL_MENU);
//    model.addAttribute("submenu", "public");
//    return "profile/public";
//  }
}
