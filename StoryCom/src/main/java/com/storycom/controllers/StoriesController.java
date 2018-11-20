package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Story;
import com.storycom.repository.StoriesRepository;
import com.storycom.services.StoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;

@Controller
@RequestMapping(value = "/story")
public class StoriesController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String GLOBAL_MENU = "story";

    @Autowired
    private StoriesService storiesService;

    @Autowired
    private StoriesRepository storiesRepository;

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

    @GetMapping(value = "/search")
    public String prepareSearchStory() {
        return "stories/searchStory";
    }

    @ResponseBody
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Story> searchStory(@RequestParam(name = "title") String title) {

        List<Story> stories = storiesRepository.findAllByTitle(title);

        return stories;
    }
}
