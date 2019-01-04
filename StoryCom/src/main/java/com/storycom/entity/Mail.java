package com.storycom.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MAILBOX")
@DynamicUpdate
public class Mail implements Serializable {
    @Id
    @SequenceGenerator(name = "SEQ_GEN_MAILS", sequenceName = "S_MAILS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_MAILS")
    @Column(name = "MAIL_ID")
    private Integer mailId;

    @Column(name = "SENDER")
    @Size(max = 250)
    @NotNull
    private String sender;

    @Column(name = "RECEIVERS")
    @Size(max = 2000)
    @NotNull
    private String receivers;

    @Column(name = "SUBJECT")
    @Size(max = 300)
    @NotNull
    private String subject;

    @Column(name = "CONTENT")
    @NotNull
    private String content;
}
