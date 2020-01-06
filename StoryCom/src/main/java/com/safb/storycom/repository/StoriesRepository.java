package com.safb.storycom.repository;

import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.entity.StoryEntity;

import java.util.List;
import org.springframework.data.jpa.repository.*;

public interface StoriesRepository extends JpaRepository<StoryEntity, Integer>
{
  @Override
  List<StoryEntity> findAll();

  StoryEntity findByStoryId(Integer storyId);

  List<StoryEntity> findAllByTitleContainingOrderByStoryIdDesc(String title);

  List<StoryEntity> findAllByUserOrderByStoryIdDesc(UserEntity user);
}
