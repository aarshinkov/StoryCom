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

        log.debug(story.toString());

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             CallableStatement cstmt = conn.prepareCall("{? = call STORYCOM_GENERAL.INSERT_STORY(?,?,?)}")) {

            log.debug(story.getTitle());
            cstmt.setString(1, story.getTitle());
            log.debug(story.getContent());
            cstmt.setString(2, story.getContent());
            log.debug("" + user.getUserId());
            cstmt.setInt(3, user.getUserId());

            cstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error adding story to database!");
        }
    }
}
