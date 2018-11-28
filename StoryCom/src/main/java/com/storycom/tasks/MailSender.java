package com.storycom.tasks;

import com.storycom.beans.ConfigurationBean;
import com.storycom.beans.settings.EmailSettings;
import com.storycom.entity.Mail;
import com.storycom.entity.Story;
import com.storycom.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailSender {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ConfigurationBean configBean;

    private JavaMailSender sender;

    @Async
    public void sendSimpleMail(SimpleMailMessage message) {
        try {
            log.debug("Sending mail");
            sender.send(message);
            log.debug("Mail sent successfully - here");
        } catch (Exception e) {
            log.debug("Error sending mail!");
        }
    }

    @Async
    public void sendMail(Mail mail, User user, Story story) {
        log.debug("Sending mail: " + mail.getMailId());

        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(mail.getSubject());
            message.setFrom(mail.getSender());
            message.setTo(user.getEmail());
            message.setText(mail.getContent(), true); //true = isHtml

            sender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Sending mail with id \'" + mail.getMailId() + "\' failed!");
        }
    }

    @PostConstruct
    private void init() {
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
