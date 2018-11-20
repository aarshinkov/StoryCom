package com.storycom.services;

import com.storycom.entity.Story;
import com.storycom.entity.User;

public interface StoriesService {
    void addStory(Story story, User user);
}
