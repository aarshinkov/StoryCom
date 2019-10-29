package com.safb.storycom.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "mailbox")
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

  public Integer getMailId()
  {
    return mailId;
  }

  public void setMailId(Integer mailId)
  {
    this.mailId = mailId;
  }

  public String getSender()
  {
    return sender;
  }

  public void setSender(String sender)
  {
    this.sender = sender;
  }

  public String getReceivers()
  {
    return receivers;
  }

  public void setReceivers(String receivers)
  {
    this.receivers = receivers;
  }

  public String getSubject()
  {
    return subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  public String getContent()
  {
    return content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }
}
