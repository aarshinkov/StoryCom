package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Story;
import com.storycom.repository.StoriesRepository;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
    public String prepareSearchStory(Model model) {

        model.addAttribute("globalMenu", GLOBAL_MENU);

        return "stories/searchStory";
    }

    @GetMapping(value = "/search", params = "title")
    public String searchStory(@RequestParam(name = "title") String title, Model model) {

        List<Story> stories = storiesRepository.findAllByTitleContaining(title);

        model.addAttribute("currUserId", getStoryUser().getUserId());
        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("stories", stories);
        model.addAttribute("storiesCount", stories.size());

        return "stories/searchStory";
    }
}
