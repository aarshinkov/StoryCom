package com.safb.storycom.repository;

import com.safb.storycom.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailsRepository extends JpaRepository<Mail, Integer>
{
  void findByMailId(Integer mailId);
}
