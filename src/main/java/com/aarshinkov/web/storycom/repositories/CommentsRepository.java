package com.aarshinkov.web.storycom.repositories;

import com.aarshinkov.web.storycom.entities.*;
import java.util.*;
import org.springframework.data.domain.*;
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
  Page<CommentEntity> findByPostPostId(Long postId, Pageable pageable);

  Optional<CommentEntity> findByCommentIdAndPostPostId(Long commentId, Long postId);
}
