package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Story;
import com.storycom.entity.User;
import com.storycom.repository.StoriesRepository;
import com.storycom.repository.UsersRepository;
import com.storycom.services.MailService;
import com.storycom.services.StoriesService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping(value = "/story")
public class StoriesController extends Base {

    private static final String GLOBAL_MENU = "story";

    @Autowired
    private StoriesService storiesService;

    @Autowired
    private MailService mailService;

    @Autowired
    private StoriesRepository storiesRepo;

    @Autowired
    private UsersRepository usersRepo;

    @GetMapping(value = "/add")
    public String prepareAddStory(Model model) {
        log.debug("prepareAddStory() begin ---");

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "add");
        model.addAttribute("story", new Story());

        return "stories/add";
    }

    @PostMapping(value = "/add")
    public String addStory(@Valid Story story, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("globalMenu", GLOBAL_MENU);
            model.addAttribute("submenu", "add");

            return "stories/add";
        }

        storiesService.addStory(story, getUser());

        return "redirect:/stories/search";
    }

    @GetMapping(value = "/search")
    public String prepareSearchStory(Model model) {

        List<Story> stories = storiesRepo.findAll();

        if (getStoryUser() != null) {
            model.addAttribute("currUserId", getStoryUser().getUserId());
        }

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "search");
        model.addAttribute("stories", stories);
        model.addAttribute("storiesCount", stories.size());

        return "stories/search";
    }

    @GetMapping(value = "/search", params = "title")
    public String searchStory(@RequestParam(name = "title") String title, Model model) {

        List<Story> stories = storiesRepo.findAllByTitleContainingOrderByStoryIdDesc(title);

        if (getStoryUser() != null) {
            model.addAttribute("currUserId", getStoryUser().getUserId());
        }

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "search");
        model.addAttribute("stories", stories);
        model.addAttribute("storiesCount", stories.size());

        return "stories/search";
    }

    @GetMapping(value = "/view", params = "id")
    public String viewStory(@RequestParam(name = "id") Integer storyId, Model model) {

        log.debug("Viewing story with id: " + storyId);

        Story story = storiesRepo.findByStoryId(storyId);

        if (story == null) {
            log.error("Error getting story by id. Object might not exist.");
            return "redirect:/error/404";
        }

        storiesService.updateStoriesViews(storyId);

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("story", story);

        return "stories/view";
    }

    @PostMapping(value = "/edit", params = "id")
    public String prepareEditStory(@RequestParam(name = "id") Integer storyId, Model model) {

        Story story = storiesRepo.findByStoryId(storyId);

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("story", story);

        return "stories/edit";
    }

    @PostMapping(value = "/edit")
    public String editStory(Story story, Model model) {
        log.debug("Editing story with id: " + story.getStoryId());

        storiesService.editStory(story);

        return "redirect:/story/search";
    }

    @PostMapping(value = "/delete", params = "id")
    public String deleteStory(@RequestParam(name = "id") Integer storyId, Model model) {
        log.debug("Deleting story with id: " + storyId);

        storiesService.deleteStory(storyId);

        return "redirect:/story/search";
    }

    @GetMapping(value = "/warn", params = "id")
    public String warnStory(@RequestParam(name = "id") Integer storyId, Model model) {

        log.debug("Warning story with id: " + storyId);

        Story story = storiesRepo.findByStoryId(storyId);
        log.debug("Story with id " + storyId + " found in the database!");

        User user = usersRepo.findByUserId(story.getUser().getUserId());
        log.debug("Author of the story found with id: " + user.getUserId());

        mailService.sendWarningMail(user, story);

        //FIXME change the redirect in the future release
        return "redirect:/";
    }

    @GetMapping(value = "/upvote", params = "id")
    public String upvoteStory(@RequestParam(name = "id") Integer storyId, Model model) {
        log.debug("Upvoting story with id: " + storyId);


        return "Upvoted";
    }

    @GetMapping(value = "/downvote", params = "id")
    public String downvoteStory(@RequestParam(name = "id") Integer storyId, Model model) {
        log.debug("Downvoting story with id: " + storyId);

        return "Downvoted";
    }
}
