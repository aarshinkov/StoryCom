package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.enums.*;
import com.aarshinkov.web.storycom.models.stories.*;
import com.aarshinkov.web.storycom.services.*;
import com.aarshinkov.web.storycom.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import javax.servlet.http.*;
import javax.validation.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class StoriesController
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private StoryService storyService;

  @Autowired
  private SystemService systemService;

  @Autowired
  private ModelMapper mapper;

  @GetMapping(value = "/stories")
  public String getStories(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
          @RequestParam(value = "limit", defaultValue = "8", required = false) Integer limit,
          @RequestParam(value = "cat", defaultValue = "", required = false) String category,
          Model model)
  {
    List<StoryDto> stories = storyService.getStories(page, limit, category);

    model.addAttribute("storiesCount", stories.size());

    model.addAttribute("stories", stories);

    int start = (page - 1) * limit + 1;
    Long globalCount = storyService.getStoriesCount();
    int end = start + stories.size() - 1;

    Page pageWrapper = new PageImpl();
    pageWrapper.setCurrentPage(page);
    pageWrapper.setMaxElementsPerPage(limit);
    pageWrapper.setStartPage(start);
    pageWrapper.setEndPage(end);
    pageWrapper.setLocalTotalElements(new Long(stories.size()));
    pageWrapper.setGlobalTotalElements(globalCount);

    model.addAttribute("pageWrapper", pageWrapper);
    model.addAttribute("maxPagesPerView", 5);

    model.addAttribute("globalMenu", "stories");

    return "stories/stories";
  }

  @GetMapping(value = "/story/{storyId}")
  public String getStory(@PathVariable(value = "storyId") Long storyId, Model model)
  {
    StoryDto story = storyService.getStoryByStoryId(storyId);

    model.addAttribute("story", story);

    storyService.readStory(story.getStoryId());

    return "stories/story";
  }

  @GetMapping(value = "/story/create")
  public String prepareCreateStory(StoryCreateModel scm, Model model)
  {
    model.addAttribute("story", scm);

    model.addAttribute("globalMenu", "stories");

    return "stories/create";
  }

  @PostMapping(value = "/story/create")
  public String createStory(@ModelAttribute("story") @Valid StoryCreateModel scm, BindingResult bindingResult,
          HttpServletRequest request, Model model)
  {
    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", "stories");
      return "stories/create";
    }

    Long userId = (Long) systemService.getSessionAttribute(request, "userId");

    StoryDto createdStory = storyService.createStory(scm, userId);

    return "redirect:/stories";
  }

  @GetMapping(value = "/story/edit/{storyId}")
  public String prepareEditStory(@PathVariable(value = "storyId") Long storyId, Model model)
  {
    StoryDto storyDto = storyService.getStoryByStoryId(storyId);

    StoryEditModel story = new StoryEditModel();
    mapper.map(storyDto, story);

    model.addAttribute("story", story);

    model.addAttribute("globalMenu", "stories");

    return "stories/edit";
  }

  @PostMapping(value = "/story/edit")
  public String editStory(@ModelAttribute("story") @Valid StoryEditModel sem, BindingResult bindingResult,
          HttpServletRequest request, Model model)
  {
    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", "stories");
      return "stories/create";
    }

    StoryDto updatedStory = storyService.updateStory(sem);

    return "redirect:/stories";
  }

  @PostMapping(value = "/story/delete")
  public String deleteStory(@RequestParam(name = "storyId") Long storyId)
  {
    storyService.deleteStory(storyId);
    return "redirect:/stories";
  }

  @GetMapping(value = "/categories/count")
  public String getCategoriesCount(Model model)
  {
    model.addAttribute("love", storyService.getStoriesCountByCategory(Categories.LOVE.getValue()));
    model.addAttribute("teen", storyService.getStoriesCountByCategory(Categories.TEEN.getValue()));
    model.addAttribute("family", storyService.getStoriesCountByCategory(Categories.FAMILY.getValue()));
    model.addAttribute("health", storyService.getStoriesCountByCategory(Categories.HEALTH.getValue()));
    model.addAttribute("education", storyService.getStoriesCountByCategory(Categories.EDUCATION.getValue()));
    model.addAttribute("sport", storyService.getStoriesCountByCategory(Categories.SPORT.getValue()));

    return "stories/sideMenu :: #categoryList";
  }
}
