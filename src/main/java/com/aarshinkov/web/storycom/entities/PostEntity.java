package com.aarshinkov.web.storycom.entities;

import java.io.*;
import javax.persistence.*;
import lombok.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "posts")
public class PostEntity implements Serializable
{

  @Id
  @SequenceGenerator(name = "seq_gen_post", sequenceName = "s_posts", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_post")
  @Column(name = "post_id")
  private Long postId;

  @Column(name = "name")
  private String name;
}
