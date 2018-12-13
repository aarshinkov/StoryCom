package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.User;
import com.storycom.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends Base {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/users")
    public String users(Model model) {
        List<User> users = usersRepository.findAllByOrderByUserIdDesc();

        model.addAttribute("users", users);
        return "admin/users/users";
    }
}
