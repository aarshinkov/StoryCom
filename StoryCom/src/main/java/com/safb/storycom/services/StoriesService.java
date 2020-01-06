package com.safb.storycom.services;

import com.safb.storycom.entity.*;
import java.util.*;

public interface StoriesService
{
  List<StoryEntity> getAllStories();
  
  void addStory(StoryEntity story, UserEntity user);

  void updateStoriesViews(Integer storyId);

  boolean editStory(StoryEntity story);

  boolean deleteStory(Integer storyId);
}
