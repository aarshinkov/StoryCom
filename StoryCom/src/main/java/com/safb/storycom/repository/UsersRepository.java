package com.safb.storycom.repository;

import com.safb.storycom.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.*;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer>
{
  List<UserEntity> findAllByOrderByUserIdDesc();

  UserEntity findByEmail(String email);

  UserEntity findByUserId(Integer userId);
}
