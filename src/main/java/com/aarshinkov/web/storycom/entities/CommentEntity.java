package com.aarshinkov.web.storycom.entities;

import com.fasterxml.jackson.annotation.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

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
@Table(name = "comments")
public class CommentEntity implements Serializable
{

  @Id
  @SequenceGenerator(name = "seq_gen_comment", sequenceName = "s_comments", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_comment")
  @Column(name = "comment_id")
  private Long commentId;

  @Column(name = "text")
  private String text;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "post_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private PostEntity post;
}
