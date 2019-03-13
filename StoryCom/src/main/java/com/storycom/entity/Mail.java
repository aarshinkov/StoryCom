package com.storycom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "mailbox")
@DynamicUpdate
public class Mail implements Serializable
{
  @Id
  @SequenceGenerator(name = "SEQ_GEN_MAIL", sequenceName = "S_MAILS", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_MAIL")
  @Column(name = "mail_id")
  private Integer mailId;

  @Column(name = "sender")
  @Size(max = 250)
  @NotNull
  private String sender;

  @Column(name = "receivers")
  @Size(max = 2000)
  @NotNull
  private String receivers;

  @Column(name = "subject")
  @Size(max = 300)
  @NotNull
  private String subject;

  @Column(name = "content")
  @NotNull
  private String content;
}
