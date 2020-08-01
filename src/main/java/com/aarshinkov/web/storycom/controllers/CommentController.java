package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.repositories.*;
import java.util.*;
import javax.validation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@RestController
public class CommentController
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private CommentsRepository commentsRepository;

  @Autowired
  private PostsRepository postsRepository;

  @GetMapping("/posts/{postId}/comments")
  public Page<CommentEntity> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId, Pageable pageable)
  {
    return commentsRepository.findByPostPostId(postId, pageable);
  }

  @PostMapping("/posts/{postId}/comments")
  public CommentEntity createComment(@PathVariable(value = "postId") Long postId,
          @Valid @RequestBody CommentEntity comment) throws Exception
  {
    return postsRepository.findById(postId).map(post ->
    {
      comment.setPost(post);
      return commentsRepository.save(comment);
    }).orElseThrow(() -> new Exception("PostId " + postId + " not found"));
  }

  @PutMapping("/posts/{postId}/comments/{commentId}")
  public CommentEntity updateComment(@PathVariable(value = "postId") Long postId,
          @PathVariable(value = "commentId") Long commentId,
          @Valid @RequestBody CommentEntity commentRequest) throws Exception
  {
    if (!postsRepository.existsById(postId))
    {
      throw new Exception("PostId " + postId + " not found");
    }

    return commentsRepository.findById(commentId).map(comment ->
    {
      Optional<PostEntity> post = postsRepository.findById(postId);
      comment.setText(commentRequest.getText());
      comment.setPost(post.get());
      return commentsRepository.save(comment);
    }).orElseThrow(() -> new Exception("CommentId " + commentId + "not found"));
  }

  @DeleteMapping("/posts/{postId}/comments/{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
          @PathVariable(value = "commentId") Long commentId) throws Exception
  {
    return commentsRepository.findByCommentIdAndPostPostId(commentId, postId).map(comment ->
    {
      commentsRepository.delete(comment);
      return ResponseEntity.ok().build();
    }).orElseThrow(() -> new Exception("Comment not found with id " + commentId + " and postId " + postId));
  }
}
