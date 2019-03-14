package com.storycom.repository;

import com.storycom.entity.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoriesRepository extends CrudRepository<Story, Integer> {
    List<Story> findAll();
    Story findByStoryId(Integer storyId);
    List<Story> findAllByTitleContainingOrderByStoryIdDesc(String title);
    List<Story> findAllByUser(User user);
}
