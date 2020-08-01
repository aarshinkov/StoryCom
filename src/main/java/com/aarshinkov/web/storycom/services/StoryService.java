package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.models.stories.*;
import java.util.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public interface StoryService
{

  /**
   * Gets stories based on the current page and limit parameters.
   *
   * @param page the current page
   * @param limit the stories per page
   * @param category gets stories based on selected category. Searches for all stories if no category specified
   * @return the stories based on the search
   */
  List<StoryDto> getStories(Integer page, Integer limit, String category);

  /**
   * Gets a story, stored in the database, based on the ID parameter
   *
   * @param storyId the id of the story
   * @return the stored story
   */
  StoryDto getStoryByStoryId(Long storyId);

  StoryDto createStory(StoryCreateModel scm, Long userId);

  StoryDto updateStory(Long storyId, StoryEditModel sem);

  void deleteStory(Long storyId);

  void readStory(Long storyId);

  Long getStoriesCountByCategory(Long categoryId);

  Long getStoriesCount();
}
