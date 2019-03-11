package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.User;
import com.storycom.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends Base {

    private static final String GLOBAL_MENU = "admin";

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/users")
    public String users(Model model) {
        List<User> users = usersRepository.findAllByOrderByUserIdDesc();

        model.addAttribute("users", users);

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "users");
        model.addAttribute("currUserId", getStoryUser().getUserId());

        return "admin/users/users";
    }

    @GetMapping(value = "/addUser")
    public String prepareAddUser(Model model) {

        User user = new User();

        model.addAttribute("user", user);

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "addUser");

        return "admin/users/createAdmin";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("globalMenu", GLOBAL_MENU);
            model.addAttribute("submenu", "addUser");

            return "admin/users/createAdmin";
        }

        log.debug("username: " + user.getUsername());
        log.debug("password: " + user.getPassword());
        log.debug("first name: " + user.getFirstName());
        log.debug("last name: " + user.getLastName());
        log.debug("email: " + user.getEmail());
        

        return "admin/users/createAdmin";
    }
}
