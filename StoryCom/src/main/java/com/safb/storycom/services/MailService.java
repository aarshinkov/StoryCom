package com.safb.storycom.services;

import com.safb.storycom.entity.MailEntity;
import com.safb.storycom.entity.StoryEntity;
import com.safb.storycom.entity.UserEntity;

public interface MailService
{
  MailEntity createMail(String sender, String subject, String email, String... recipients);

  void sendSimpleWarningMail(UserEntity user, StoryEntity story);

  void sendWarningMail(UserEntity user, StoryEntity story);
}
