package com.aarshinkov.web.storycom.repositories;

import com.aarshinkov.web.storycom.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long>
{
  CommentEntity findByCommentId(Long commentId);

  Long countByStoryStoryId(Long storyId);
}
