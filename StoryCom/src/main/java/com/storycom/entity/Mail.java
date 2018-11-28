package com.storycom.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "MAILBOX")
@DynamicUpdate
public class Mail implements Serializable {
    @Id
    @SequenceGenerator(name = "SEQ_GEN_MAILS", sequenceName = "S_MAILS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_MAILS")
    @Column(name = "MAIL_ID")
    private int mailId;

    @Column(name = "SUBJECT")
    @Size(max = 300)
    @NotNull
    private String subject;

    @Column(name = "SENDER")
    @Size(max = 250)
    @NotNull
    private String sender;

    @Column(name = "RECEIVERS")
    @Size(max = 2000)
    @NotNull
    private String receivers;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

    public String toString() {
        return "mailId: " + getMailId() + "; subject: " + getSubject() + "; receivers: " + getReceivers();
    }

    public int getMailId() {
        return mailId;
    }

    public void setMailId(int mailId) {
        this.mailId = mailId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
