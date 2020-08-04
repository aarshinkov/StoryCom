package com.aarshinkov.web.storycom.repositories;

import com.aarshinkov.web.storycom.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Repository
public interface StoriesRepository extends JpaRepository<StoryEntity, Long>
{
  List<StoryEntity> findByOrderByCreatedOnDesc();

  StoryEntity findByStoryId(Long storyId);

  Long countByCategory(CategoryEntity category);

  Long countByUser(UserEntity user);
}
