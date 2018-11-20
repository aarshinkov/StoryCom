package com.storycom.repository;

import com.storycom.entity.Story;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoriesRepository extends CrudRepository<Story, Integer> {
    Story findByStoryId(Integer storyId);
    List<Story> findAllByTitle(String title);
}
