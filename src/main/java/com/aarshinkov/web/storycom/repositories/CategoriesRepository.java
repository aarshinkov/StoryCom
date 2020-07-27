package com.aarshinkov.web.storycom.repositories;

import com.aarshinkov.web.storycom.entities.CategoryEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface CategoriesRepository extends JpaRepository<CategoryEntity, Long>
{
  CategoryEntity findByCategoryId(Long categoryId);

  Long countByCategoryId(Long categoryId);

  CategoryEntity findByName(String name);

  Long countByName(String name);
}
