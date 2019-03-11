package com.storycom.repository;

import com.storycom.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailsRepository extends JpaRepository<Mail, Integer> {
    void findByMailId(Integer mailId);
}
