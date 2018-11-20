package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Story;
import com.storycom.services.StoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/story")
public class StoriesController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String GLOBAL_MENU = "story";

    @Autowired
    private StoriesService storiesService;

    @GetMapping(value = "/add")
    public String prepareAddStory(Model model) {
        log.debug("prepareAddStory() begin ---");

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("story", new Story());

        return "stories/addStory";
    }

    @PostMapping(value = "/add")
    public String addStory(@Valid Story story, BindingResult bindingResult, Model model) {

        storiesService.addStory(story, getUser());

        return "redirect:/";
    }
}
