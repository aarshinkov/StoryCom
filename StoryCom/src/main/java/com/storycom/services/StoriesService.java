package com.storycom.services;

import com.storycom.entity.Story;
import com.storycom.entity.User;

public interface StoriesService {
    void updateStoriesViews(Integer storyId);
    void addStory(Story story, User user);
    boolean deleteStory(Integer storyId);
}
