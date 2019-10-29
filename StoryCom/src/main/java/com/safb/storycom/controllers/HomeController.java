package com.safb.storycom.controllers;

import com.safb.storycom.base.Base;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController extends Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String GLOBAL_MENU = "home";

  @ModelAttribute
  public void addAttributes(Model model)
  {
    model.addAttribute("globalMenu", GLOBAL_MENU);
  }

  @GetMapping(value = "/")
  public String home(Model model)
  {

    model.addAttribute("submenu", "home");

    return "home/home";
  }

  @GetMapping(value = "/about")
  public String about(Model model)
  {

    model.addAttribute("submenu", "about");

    return "home/about";
  }

  @GetMapping(value = "/contact")
  public String prepareContact(Model model)
  {

    model.addAttribute("submenu", "contact");

    return "home/contact";
  }

  @PostMapping(value = "/contact")
  public String contactUs(Model model)
  {
    return "redirect:/about/contact";
  }

  @GetMapping(value = "/timeline")
  public String timeline(Model model)
  {

    model.addAttribute("submenu", "timeline");

    return "home/timeline";
  }
}
