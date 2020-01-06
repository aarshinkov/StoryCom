package com.safb.storycom.entity;

import java.io.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mailbox")
public class MailEntity implements Serializable
{
  @Id
  @SequenceGenerator(name = "seq_gen_mail", sequenceName = "s_mails", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_mail")
  @Column(name = "mail_id")
  private Integer mailId;

  @Column(name = "sender")
  private String sender;

  @Column(name = "receivers")
  private String receivers;

  @Column(name = "subject")
  private String subject;

  @Column(name = "content")
  private String content;
}
