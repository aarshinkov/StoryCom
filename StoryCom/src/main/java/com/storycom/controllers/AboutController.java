package com.storycom.controllers;

import com.storycom.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/about")
public class AboutController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String GLOBAL_MENU = "about";

    @GetMapping
    public String about(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "about");

        return "about/about";
    }

    @GetMapping(value = "/contact")
    public String prepareContact(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "contact");

        return "about/contact";
    }

    @PostMapping(value = "/contact")
    public String contactUs(Model model) {
        return "redirect:/about/contact";
    }

    @GetMapping(value = "/timeline")
    public String timeline(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "timeline");

        return "about/timeline";
    }

}
