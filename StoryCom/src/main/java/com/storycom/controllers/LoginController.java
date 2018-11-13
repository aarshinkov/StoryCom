package com.storycom.controllers;

import com.storycom.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/login")
    public String preparelogin(Model model) {
        model.addAttribute("globalMenu", "login");

        return "login/login";
    }
}
