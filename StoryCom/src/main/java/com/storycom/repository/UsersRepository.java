package com.storycom.repository;

import com.storycom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Integer> {
    List<User> findAllByOrderByUserIdDesc();
    User findUserByUsername(String username);
    User findByUserId(Integer userId);
}
