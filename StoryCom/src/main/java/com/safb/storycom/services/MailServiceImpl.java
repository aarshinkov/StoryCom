package com.safb.storycom.services;

import com.safb.storycom.beans.ConfigurationBean;
import com.safb.storycom.entity.Mail;
import com.safb.storycom.entity.Story;
import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.repository.MailsRepository;
import com.safb.storycom.tasks.MailSender;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private MailSender mailSender;

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private ConfigurationBean configBean;

  @Autowired
  private MailsRepository mailsRepo;

  @Override
  public Mail createMail(String sender, String subject, String content, String... recipients)
  {
    StringBuilder to = new StringBuilder();
    for (String recipient : recipients)
    {
      to.append(recipient);
      to.append(";");
    }
    String recips = to.substring(0, to.length() - 1);
    log.debug("Recips: " + recips);

    Mail mail = new Mail();
    mail.setSender(sender);
    mail.setReceivers(recips);
    mail.setSubject(subject);
    mail.setContent(content);

    return mailsRepo.save(mail);
  }

  @Override
  public void sendSimpleWarningMail(UserEntity user, Story story)
  {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject("Warning");
    message.setText("You have been warned!");
    mailSender.sendSimpleMail(message);
  }

  @Override
  public void sendWarningMail(UserEntity user, Story story)
  {
    try
    {
      Context ctx = new Context();
      ctx.setVariable("appUrl", configBean.getAppUrl());
      ctx.setVariable("firstName", user.getFirstName());
      ctx.setVariable("lastName", user.getLastName());
      ctx.setVariable("gender", user.getUserDetail().getGender());
      ctx.setVariable("title", story.getTitle());
      ctx.setVariable("content", story.getContent());
      ctx.setVariable("createdOn", story.getCreatedOn());

      String htmlContent = templateEngine.process("warningStoryMail.html", ctx);

      Mail mail = createMail(configBean.getEmailSettings().getSender(), "Warning for inappropriate story", htmlContent, user.getEmail());
      mailSender.sendMail(mail, user, story);

    }
    catch (Exception e)
    {
      log.error("Sending failed!");
    }
  }
}
