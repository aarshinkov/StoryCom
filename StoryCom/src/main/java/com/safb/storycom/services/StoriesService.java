package com.safb.storycom.services;

import com.safb.storycom.entity.Story;
import com.safb.storycom.entity.UserEntity;

public interface StoriesService
{
  void updateStoriesViews(Integer storyId);

  void addStory(Story story, UserEntity user);

  boolean editStory(Story story);

  boolean deleteStory(Integer storyId);
}
