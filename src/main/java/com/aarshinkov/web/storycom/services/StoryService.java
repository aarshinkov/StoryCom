package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.collections.*;
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
  ObjCollection<StoryDto> getStories(Integer page, Integer limit, String category, Long userId);

  /**
   * Gets a story, stored in the database, based on the ID parameter
   *
   * @param storyId the id of the story
   * @return the stored story
   */
  StoryDto getStoryByStoryId(Long storyId);

  StoryDto createStory(StoryCreateModel scm, Long userId);

  StoryDto updateStory(Long storyId, StoryEditModel sem);

  StoryDto deleteStory(Long storyId);

  void readStory(Long storyId);

  Long getStoriesCountByCategory(Long categoryId);

  Long getStoriesCount();

  Long getStoriesCountByUser(Long userId);

  List<CommentDto> getStoryComments(Long storyId, Integer page, Integer limit);

  CommentDto createComment(CommentCreateModel ccm);

  Long getStoryCommentsCount(Long storyId);
}
