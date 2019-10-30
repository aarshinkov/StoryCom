package com.safb.storycom.controllers;

import com.safb.storycom.base.Base;
import com.safb.storycom.entity.Story;
import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.repository.StoriesRepository;
import com.safb.storycom.repository.UsersRepository;
import com.safb.storycom.services.MailService;
import com.safb.storycom.services.StoriesService;
import java.text.*;
import java.util.*;
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
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/stories")
public class StoriesController extends Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String GLOBAL_MENU = "stories";

  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  @Autowired
  private StoriesService storiesService;

  @Autowired
  private MailService mailService;

  @Autowired
  private StoriesRepository storiesRepo;

  @Autowired
  private UsersRepository usersRepo;

  @GetMapping(value = "/search")
  public String prepareSearchStory(Model model)
  {
    List<Story> stories = storiesRepo.findAll();

    for (Story story : stories)
    {
      story.setCreatedOnFormatted(sdf.format(story.getCreatedOn()));
    }

    if (getLoggedUser() != null)
    {
      model.addAttribute("currUserId", getLoggedUser().getUserId());
    }

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "search");
    model.addAttribute("stories", stories);
    model.addAttribute("storiesCount", stories.size());

    return "stories/search";
  }

  @GetMapping(value = "/search", params = "title")
  public String searchStory(@RequestParam(name = "title") String title, Model model)
  {
    if (title.equals(""))
    {
      return "redirect:/stories/search";
    }

    List<Story> stories = storiesRepo.findAllByTitleContainingOrderByStoryIdDesc(title);

    for (Story story : stories)
    {
      story.setCreatedOnFormatted(sdf.format(story.getCreatedOn()));
    }

    if (getLoggedUser() != null)
    {
      model.addAttribute("currUserId", getLoggedUser().getUserId());
    }

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "search");
    model.addAttribute("stories", stories);
    model.addAttribute("storiesCount", stories.size());

    return "stories/search";
  }

  @GetMapping(value = "/{username}")
  public String myStories(@PathVariable("username") String username, Model model)
  {
    UserEntity user = usersRepo.findByUsername(username);
    List<Story> stories = storiesRepo.findAllByUserOrderByStoryIdDesc(user);

    for (Story story : stories)
    {
      story.setCreatedOnFormatted(sdf.format(story.getCreatedOn()));
    }

    if (getLoggedUser() != null)
    {
      model.addAttribute("currUserId", getLoggedUser().getUserId());
    }

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "mine");
    model.addAttribute("stories", stories);
    model.addAttribute("storiesCount", stories.size());
    model.addAttribute("user", user);

    return "stories/myStories";
  }

  @GetMapping(value = "/add")
  public String prepareAddStory(Model model)
  {
    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("submenu", "add");
    model.addAttribute("story", new Story());

    return "stories/add";
  }

  @PostMapping(value = "/add")
  public String addStory(@Valid Story story, BindingResult bindingResult, Model model)
  {
    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", GLOBAL_MENU);
      model.addAttribute("submenu", "add");

      return "stories/add";
    }

    storiesService.addStory(story, getUser());

    return "redirect:/stories/search";
  }

  @GetMapping(value = "/view", params = "id")
  public String viewStory(@RequestParam(name = "id") Integer storyId, Model model)
  {
    Story story = storiesRepo.findByStoryId(storyId);

    if (story == null)
    {
      log.error("Error getting story by id. Object might not exist.");
      return "redirect:/error/404";
    }

    storiesService.updateStoriesViews(storyId);

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("story", story);

    return "stories/view";
  }

  @PostMapping(value = "/edit", params = "id")
  public String prepareEditStory(@RequestParam(name = "id") Integer storyId, Model model)
  {
    Story story = storiesRepo.findByStoryId(storyId);

    model.addAttribute("globalMenu", GLOBAL_MENU);
    model.addAttribute("story", story);

    return "stories/edit";
  }

  @PostMapping(value = "/edit")
  public String editStory(Story story, Model model)
  {
    storiesService.editStory(story);

    return "redirect:/stories/search";
  }

  @PostMapping(value = "/delete", params = "id")
  public String deleteStory(@RequestParam(name = "id") Integer storyId, Model model)
  {
    storiesService.deleteStory(storyId);

    return "redirect:/stories/search";
  }

  @GetMapping(value = "/warn", params = "id")
  public String warnStory(@RequestParam(name = "id") Integer storyId, Model model)
  {
    Story story = storiesRepo.findByStoryId(storyId);

    UserEntity user = usersRepo.findByUserId(story.getUser().getUserId());

    mailService.sendWarningMail(user, story);

    //FIXME change the redirect in the future release
    return "redirect:/";
  }

  @GetMapping(value = "/upvote", params = "id")
  public String upvoteStory(@RequestParam(name = "id") Integer storyId, Model model)
  {
    return "Upvoted";
  }

  @GetMapping(value = "/downvote", params = "id")
  public String downvoteStory(@RequestParam(name = "id") Integer storyId, Model model)
  {
    return "Downvoted";
  }
}
