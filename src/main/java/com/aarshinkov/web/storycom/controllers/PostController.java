package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.repositories.*;
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
public class PostController
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private PostsRepository postsRepository;

  @GetMapping("/posts")
  public Page<PostEntity> getAllPosts(Pageable pageable)
  {
    return postsRepository.findAll(pageable);
  }

  @PostMapping("/posts")
  public PostEntity createPost(@Valid @RequestBody PostEntity post)
  {
    return postsRepository.save(post);
  }

  @PutMapping("/posts/{postId}")
  public PostEntity updatePost(@PathVariable Long postId, @Valid @RequestBody PostEntity postRequest) throws Exception
  {
    return postsRepository.findById(postId).map(post ->
    {
      post.setName(postRequest.getName());
      return postsRepository.save(post);
    }).orElseThrow(() -> new Exception("PostId " + postId + " not found"));
  }

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<?> deletePost(@PathVariable Long postId) throws Exception
  {
    return postsRepository.findById(postId).map(post ->
    {
      postsRepository.delete(post);
      return ResponseEntity.ok().build();
    }).orElseThrow(() -> new Exception("PostId " + postId + " not found"));
  }

}
