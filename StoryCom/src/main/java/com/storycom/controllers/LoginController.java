package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.domain.RegisterUser;
import com.storycom.entity.User;
import com.storycom.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String prepareLogin(Model model) {
        model.addAttribute("globalMenu", "login");

        return "login/login";
    }

    @GetMapping(value = "/signup")
    public String prepareSignup(Model model) {

        RegisterUser registerUser = new RegisterUser();

        model.addAttribute("regUser", registerUser);
        model.addAttribute("globalMenu", "signup");

        return "login/signup";
    }

    @PostMapping(value = "/signup")
    public String signup(RegisterUser registerUser, BindingResult bindingResult) {

        log.debug("RegisterUser: " + registerUser.toString());

        User user = userService.registerUserToUser(registerUser);

        log.debug("User: " + user.toString());

        userService.registerUser(user);

        return "redirect:/login";
    }
}
