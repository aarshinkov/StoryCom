package com.safb.storycom.repository;

import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.entity.Story;

import java.util.List;
import org.springframework.data.jpa.repository.*;

public interface StoriesRepository extends JpaRepository<Story, Integer>
{
  @Override
  List<Story> findAll();

  Story findByStoryId(Integer storyId);

  List<Story> findAllByTitleContainingOrderByStoryIdDesc(String title);

  List<Story> findAllByUserOrderByStoryIdDesc(UserEntity user);
}
