package com.storycom.services;

import com.storycom.beans.ConfigurationBean;
import com.storycom.entity.Mail;
import com.storycom.entity.Story;
import com.storycom.entity.User;
import com.storycom.tasks.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ConfigurationBean configBean;

    @Override
    public Mail createMail(String sender, String subject, String content, String... recipients) {
        log.debug("Creating mail begin --");
        StringBuilder to = new StringBuilder();
        for (String recipient : recipients) {
            to.append(recipient);
            to.append(";");
        }
        String recips = to.substring(0, to.length() - 1);
        log.debug("Recips: " + recips);

        Mail mail = new Mail();
        mail.setSender(sender);
        mail.setReceivers(recips);
        mail.setContent(content);

//        for inserting the mail into the database
//        return mailsRepo.save(mail);
        log.debug("Mail: " + mail.toString());
        return mail;
    }

    @Override
    public void sendSimpleWarningMail(User user, Story story) {
        log.debug("Sending warning mail begin --");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Warning");
        message.setText("You have been warned!");
        mailSender.sendSimpleMail(message);
    }

    @Override
    public void sendWarningMail(User user, Story story) {
        try {
            Context ctx = new Context();
            ctx.setVariable("username", user.getUsername());

            String htmlContent = templateEngine.process("warningStoryMail.html", ctx);

            Mail mail = createMail(configBean.getEmailSettings().getSender(), "Warning for inappropriate story", htmlContent, user.getEmail());
            mailSender.sendMail(mail, user, story);

        } catch (Exception e) {
            log.error("Sending failed!");
        }
    }
}
