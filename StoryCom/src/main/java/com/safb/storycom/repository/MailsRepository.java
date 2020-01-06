package com.safb.storycom.repository;

import com.safb.storycom.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailsRepository extends JpaRepository<MailEntity, Integer>
{
  void findByMailId(Integer mailId);
}
