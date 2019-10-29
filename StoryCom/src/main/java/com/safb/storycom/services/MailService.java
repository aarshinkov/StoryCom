package com.safb.storycom.services;

import com.safb.storycom.entity.Mail;
import com.safb.storycom.entity.Story;
import com.safb.storycom.entity.UserEntity;

public interface MailService
{
  Mail createMail(String sender, String subject, String email, String... recipients);

  void sendSimpleWarningMail(UserEntity user, Story story);

  void sendWarningMail(UserEntity user, Story story);
}
