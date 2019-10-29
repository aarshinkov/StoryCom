package com.safb.storycom.tasks;

import com.safb.storycom.beans.ConfigurationBean;
import com.safb.storycom.beans.settings.EmailSettings;
import com.safb.storycom.entity.Mail;
import com.safb.storycom.entity.Story;
import com.safb.storycom.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.slf4j.*;

@Component
public class MailSender
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ConfigurationBean configBean;

  private JavaMailSender sender;

  @Async
  public void sendSimpleMail(SimpleMailMessage message)
  {
    try
    {
      sender.send(message);
    }
    catch (Exception e)
    {
      log.debug("Error sending mail!");
    }
  }

  @Async
  @Transactional(rollbackFor = Exception.class)
  public void sendMail(Mail mail, UserEntity user, Story story)
  {
    log.debug("Sending mail: " + mail.getMailId());

    String sqlUpd = "UPDATE MAILBOX SET IS_SENT = ? WHERE MAIL_ID = ?";

    try
    {
      MimeMessage mimeMessage = sender.createMimeMessage();
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
      message.setSubject(mail.getSubject());
      message.setFrom(mail.getSender());
      message.setTo(user.getEmail());
      message.setText(mail.getContent(), true); //true = isHtml

      sender.send(mimeMessage);

      jdbcTemplate.update(sqlUpd, "Y", mail.getMailId());
      log.debug("Mail sent: " + mail.getMailId());
    }
    catch (Exception e)
    {
      log.error("Sending mail with id = " + mail.getMailId() + " failed!");
    }
  }

  @PostConstruct
  private void init()
  {
    JavaMailSenderImpl ms = new JavaMailSenderImpl();
    EmailSettings es = configBean.getEmailSettings();
    ms.setHost(es.getHost());
    ms.setPort(es.getPort());
    ms.setProtocol(es.getProtocol());
    ms.setUsername(es.getUsername());
    ms.setPassword(es.getPassword());

    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.quitwait", "false");
    ms.setJavaMailProperties(properties);
    sender = ms;
  }
}
