package com.storycom.services;

import com.storycom.entity.Mail;
import com.storycom.entity.Story;
import com.storycom.entity.User;

public interface MailService {
    Mail createMail(String sender, String subject, String email, String... recipients);
    void sendSimpleWarningMail(User user, Story story);
    void sendWarningMail(User user, Story story);
}
