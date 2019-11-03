package com.safb.storycom.services;

import com.safb.storycom.entity.*;
import java.util.*;

public interface StoriesService
{
  List<Story> getAllStories();
  
  void addStory(Story story, UserEntity user);

  void updateStoriesViews(Integer storyId);

  boolean editStory(Story story);

  boolean deleteStory(Integer storyId);
}
