package com.storycom.controllers;

import com.storycom.base.Base;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class HomeController extends Base {

    private static final String GLOBAL_MENU = "home";

    @GetMapping(value = "/")
    public String home(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "home");

        return "home/home";
    }

    @GetMapping(value = "/about")
    public String about(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "about");

        return "home/about";
    }

    @GetMapping(value = "/contact")
    public String prepareContact(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "contact");

        return "home/contact";
    }

    @PostMapping(value = "/contact")
    public String contactUs(Model model) {
        return "redirect:/about/contact";
    }

    @GetMapping(value = "/timeline")
    public String timeline(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "timeline");

        return "home/timeline";
    }
}
