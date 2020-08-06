package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.base.*;
import com.aarshinkov.web.storycom.collections.*;
import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.enums.*;
import com.aarshinkov.web.storycom.models.stories.*;
import com.aarshinkov.web.storycom.repositories.*;
import com.aarshinkov.web.storycom.services.*;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;
import javax.validation.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.util.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;
import org.springframework.web.servlet.mvc.support.*;

@Controller
public class StoriesController extends Base
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private StoryService storyService;

  @Autowired
  private StoriesRepository storiesRepository;

  @Autowired
  private CategoriesRepository categoriesRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private SystemService systemService;

  @Autowired
  private ModelMapper mapper;

//   @PostMapping(value = "/story/edit")
//  public String editStory(@ModelAttribute("story") @Valid StoryEditModel sem, BindingResult bindingResult,
//          HttpServletRequest request, Model model)
//  {
//    if (bindingResult.hasErrors())
//    {
//      model.addAttribute("globalMenu", "stories");
//      return "stories/create";
//    }
//
//    StoryDto updatedStory = storyService.updateStory(sem);
//
//    return "redirect:/";
//  }
  @GetMapping(value = "/stories")
  public String getStories(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
          @RequestParam(value = "limit", defaultValue = "5", required = false) Integer limit,
          @RequestParam(value = "cat", defaultValue = "", required = false) String category,
          Model model)
  {
    ObjCollection<StoryDto> stories = storyService.getStories(page, limit, category, null);

    model.addAttribute("storiesCount", stories.getPage().getLocalTotalElements());

    model.addAttribute("stories", stories.getCollection());

    String otherParams = "";

    if (limit != null && limit > 0)
    {
      otherParams = "&limit=" + limit;
    }

    if (!StringUtils.isEmpty(category))
    {
      otherParams += "&cat=" + category;
    }

    model.addAttribute("otherParameters", otherParams);

    model.addAttribute("pageWrapper", stories.getPage());
    model.addAttribute("maxPagesPerView", 5);

    model.addAttribute("cat", category);

    model.addAttribute("globalMenu", "stories");

    return "stories/stories";
  }

  @GetMapping(value = "/story/{storyId}")
  public String getStory(@PathVariable(value = "storyId") Long storyId, Model model)
  {
    StoryDto story = storyService.getStoryByStoryId(storyId);

    if (story == null)
    {
      throw new ResponseStatusException(NOT_FOUND, "Story not found");
    }

    Long totalCommentsCount = storyService.getStoryCommentsCount(storyId);

    model.addAttribute("story", story);
    model.addAttribute("storyCommentsCount", totalCommentsCount);

    model.addAttribute("comment", new CommentCreateModel());

    storyService.readStory(story.getStoryId());

    model.addAttribute("globalMenu", "stories");

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
          HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
  {
    LOG.debug("scm: " + scm);

    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", "stories");
      return "stories/create";
    }

    Long userId = (Long) systemService.getSessionAttribute(request, "userId");

    try
    {
      StoryDto createdStory = storyService.createStory(scm, userId);
      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("story.create.success"));
    }
    catch (Exception e)
    {
      redirectAttributes.addFlashAttribute("msgError", getMessage("story.create.error"));
    }

    return "redirect:/stories";
  }

  @GetMapping(value = "/story/edit/{storyId}")
//  @PreAuthorize("#username == authentication.principal.username")
  public String prepareEditStory(@PathVariable(value = "storyId") Long storyId, Model model)
  {
    StoryDto storyDto = storyService.getStoryByStoryId(storyId);

    StoryEditModel story = new StoryEditModel();
    mapper.map(storyDto, story);

    model.addAttribute("story", story);
    model.addAttribute("storyId", storyId);

    model.addAttribute("globalMenu", "stories");

    return "stories/edit";
  }

  @PostMapping(value = "/story/edit/{storyId}")
  public String editStory(@ModelAttribute("story") @Valid StoryEditModel sem, BindingResult bindingResult,
          @PathVariable(value = "storyId") Long storyId, RedirectAttributes redirectAttributes, Model model)
  {
    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", "stories");
      return "stories/create";
    }

    try
    {
      storyService.updateStory(storyId, sem);
      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("story.edit.success"));
    }
    catch (Exception e)
    {
      redirectAttributes.addFlashAttribute("msgError", getMessage("story.edit.error"));
    }

    return "redirect:/story/" + storyId;
  }

//  @PostMapping(value = "/story/edit/{storyId}")
//  public String editStory(@ModelAttribute("story") @Valid StoryEditModel sem, BindingResult bindingResult,
//          @PathVariable(value = "storyId") Long storyId, Model model)
//  {
//    if (bindingResult.hasErrors())
//    {
//      model.addAttribute("globalMenu", "stories");
//      return "stories/create";
//    }
//    
//    //    StoryDto updatedStory = storyService.updateStory(sem);
//
//    StoryEntity story = storiesRepository.findByStoryId(storyId);
//
//    CategoryEntity category = categoriesRepository.findByCategoryId(sem.getCategoryId());
//
//    story.setTitle(sem.getTitle());
//    story.setStory(sem.getStory());
//    story.setCategory(category);
//
//    storiesRepository.save(story);
//
//    return "redirect:/story/" + story.getStoryId();
//  }
  @PostMapping(value = "/story/delete")
  public String deleteStory(@RequestParam(name = "storyId") Long storyId, RedirectAttributes redirectAttributes)
  {
    try
    {
      StoryDto deletedStory = storyService.deleteStory(storyId);
      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("story.delete.success"));
    }
    catch (Exception e)
    {
      LOG.debug("Error deleting story", e);
      redirectAttributes.addFlashAttribute("msgError", getMessage("story.delete.error"));
    }
    return "redirect:/stories";
  }

  @GetMapping(value = "/categories/count")
  public String getCategoriesCount(@RequestParam(name = "currentCat") String currentCategory, Model model)
  {
    if (StringUtils.isEmpty(currentCategory))
    {
      model.addAttribute("isEmpty", 0);
    }
    else
    {
      model.addAttribute("isEmpty", 1);
    }

    model.addAttribute("globalStoriesCount", storyService.getStoriesCount());

    model.addAttribute("love", storyService.getStoriesCountByCategory(Categories.LOVE.getValue()));
    model.addAttribute("teen", storyService.getStoriesCountByCategory(Categories.TEEN.getValue()));
    model.addAttribute("family", storyService.getStoriesCountByCategory(Categories.FAMILY.getValue()));
    model.addAttribute("health", storyService.getStoriesCountByCategory(Categories.HEALTH.getValue()));
    model.addAttribute("education", storyService.getStoriesCountByCategory(Categories.EDUCATION.getValue()));
    model.addAttribute("sport", storyService.getStoriesCountByCategory(Categories.SPORT.getValue()));

    model.addAttribute("currentCategory", currentCategory);

    return "stories/fragments :: #categoryList";
  }

  @GetMapping(value = "/story/comments")
  public String getComments(@RequestParam(name = "storyId") Long storyId,
          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
          Model model)
  {
    //TODO MAY BE CHANGED
    Integer limit = 4;
    List<CommentDto> comments = storyService.getStoryComments(storyId, page, limit);
    Long totalCommentsCount = storyService.getStoryCommentsCount(storyId);

    StoryDto story = storyService.getStoryByStoryId(storyId);

    boolean hasMore = totalCommentsCount > comments.size();

    model.addAttribute("hasMore", hasMore);
    model.addAttribute("story", story);

    model.addAttribute("commentsCount", comments.size());
    model.addAttribute("totalCommentsCount", totalCommentsCount);
    model.addAttribute("comments", comments);

    return "stories/fragments :: #commentDiv";
  }

  @PostMapping(value = "/story/comment/create")
  public String createComment(@ModelAttribute("comment") @Valid CommentCreateModel ccm, BindingResult bindingResult,
          RedirectAttributes redirectAttributes, Model model)
  {
    if (bindingResult.hasErrors())
    {
      StoryDto story = storyService.getStoryByStoryId(ccm.getStoryId());

      Long totalCommentsCount = storyService.getStoryCommentsCount(ccm.getStoryId());

      model.addAttribute("story", story);
      model.addAttribute("storyCommentsCount", totalCommentsCount);

      model.addAttribute("globalMenu", "stories");

      return "stories/story";
    }

    LOG.debug("comment: " + ccm.getComment());
    LOG.debug("storyId: " + ccm.getStoryId());
    LOG.debug("userId: " + ccm.getUserId());

    try
    {
      CommentDto createdComment = storyService.createComment(ccm);
      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("story.comments.create.success"));
    }
    catch (Exception e)
    {
      LOG.error("Error saving comment", e);
      redirectAttributes.addFlashAttribute("msgError", getMessage("story.comments.create.error"));
    }

    return "redirect:/story/" + ccm.getStoryId();
  }
}
