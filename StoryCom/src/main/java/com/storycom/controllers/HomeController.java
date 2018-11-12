package com.storycom.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String GLOBAL_MENU = "home";

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("globalMenu", GLOBAL_MENU);
        return "home";
    }
}
