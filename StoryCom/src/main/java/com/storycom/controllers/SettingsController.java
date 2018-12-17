package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.domain.Password;
import com.storycom.entity.User;
import com.storycom.repository.UsersRepository;
import com.storycom.security.StoryUser;
import com.storycom.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SettingsController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String GLOBAL_MENU = "settings";

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/settings/profile")
    public String viewProfile(Model model) {

        User user = getUser();

        log.debug(user.toString());

        model.addAttribute("user", user);

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "profile");

        return "settings/profile";
    }

    @PostMapping(value = "/settings/profile")
    public String saveProfile(User user, Model model) {

        //TODO get proper user/storyUser

        StoryUser storyUser = getStoryUser();

        log.debug("User id: " + storyUser.getUserId());

        //TODO call update procedure

        return "redirect:/settings/profile";
    }

    @GetMapping(value = "/settings/changepass")
    public String prepareChangePassword(Model model) {

        log.debug("Preparing change password!");

        model.addAttribute("password", new Password());

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "password");

        return "settings/changePass";
    }

    @PostMapping(value = "/settings/changepass")
    public String changePassword(@Valid Password password, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("submenu", "password");
            model.addAttribute("globalMenu", GLOBAL_MENU);

            return "settings/changePass";
        }

        log.debug("Changing password for user: " + getStoryUser().getUsername());
        log.debug("User id: " + getStoryUser().getUserId());

        log.debug("Password: " + password.getPassword());
        log.debug("Confirmed password: " + password.getConfirmPassword());

        password.setEncodedPassword(passwordEncoder.encode(password.getPassword()));

//        userService.changePassword(getStoryUser(), password);

        return "redirect:/settings/profile";
    }

}
