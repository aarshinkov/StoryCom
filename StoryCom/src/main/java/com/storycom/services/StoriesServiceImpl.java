package com.storycom.services;

import com.storycom.entity.Story;
import com.storycom.entity.User;
import com.storycom.repository.StoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;

@Service
public class StoriesServiceImpl implements StoriesService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StoriesRepository storiesRepo;

    @Override
    public void updateStoriesViews(Integer storyId) {
        try {
            Integer storyViews = storiesRepo.findByStoryId(storyId).getViews();

            String sqlUpd = "UPDATE STORIES SET VIEWS = ? WHERE STORY_ID = ?";

            storyViews++;

            jdbcTemplate.update(sqlUpd, storyViews, storyId);

        } catch (Exception e) {
            log.debug("Error updating story's views!");
        }
    }

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
