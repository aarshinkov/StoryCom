package com.storycom.services;

import com.storycom.entity.Story;
import com.storycom.entity.User;
import com.storycom.repository.StoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.util.Objects;

@Service
public class StoriesServiceImpl implements StoriesService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addStory(Story story, User user) {

        log.debug("Adding story...");

        try {
            CallableStatement cstmt = jdbcTemplate.getDataSource().getConnection().prepareCall("{call STORYCOM_GENERAL.INSERT_STORY(?,?,?)}");
            cstmt.setString(1, story.getTitle());
            cstmt.setString(2, story.getContent());
            cstmt.setInt(3, user.getUserId());

            cstmt.execute();
            log.debug("Story added successfully!");
        } catch (Exception e) {
            log.error("Error adding story to database!");
        }
    }
}
