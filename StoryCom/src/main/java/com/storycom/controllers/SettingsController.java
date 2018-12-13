package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.domain.Password;
import com.storycom.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String GLOBAL_MENU = "settings";

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "/settings/profile")
    public String viewProfile(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "profile");

        return "settings/profile";
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
    public String changePassword(Password password, Model model) {

        log.debug("Changing password for user: " + getStoryUser().getUsername());
        log.debug("User id: " + getStoryUser().getUserId());

        log.debug("Password: " + password.getPassword());
        log.debug("Confirmed password: " + password.getConfirmPassword());

        password.setEncodedPassword(passwordEncoder.encode(password.getPassword()));

        userService.changePassword(getStoryUser(), password);

        return "redirect:/settings/profile";
    }

}
