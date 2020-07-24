package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.entities.StoryEntity;
import com.aarshinkov.web.storycom.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class StoriesController
{
  @Autowired
  private StoriesRepository storiesRepository;

  @GetMapping(value = "/stories")
  public String getStories(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
          @RequestParam(value = "limit", defaultValue = "6", required = false) Integer limit,
          Model model)
  {

    model.addAttribute("globalMenu", "stories");

    List<StoryEntity> stories = storiesRepository.findAll();

    model.addAttribute("stories", stories);

    return "stories/stories";
  }

  @GetMapping(value = "/story/{storyId}")
  public String getStory(@PathVariable(value = "storyId") Long storyId, Model model)
  {
    model.addAttribute("story", storiesRepository.findByStoryId(storyId));

    return "stories/story";
  }
}
