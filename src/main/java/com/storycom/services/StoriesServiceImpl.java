package com.storycom.services;

import com.storycom.entity.Story;
import com.storycom.entity.User;
import com.storycom.repository.StoriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@Service
public class StoriesServiceImpl implements StoriesService {

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
            CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_STORIES.INSERT_STORY(?,?,?,?)}");
            cstmt.setString(1, story.getTitle());
            cstmt.setString(2, story.getOverview());
            cstmt.setString(3, story.getContent());
            cstmt.setInt(4, user.getUserId());

            cstmt.execute();
            log.debug("Story added successfully!");
        } catch (Exception e) {
            log.error("Error adding story to database!");
        }
    }

    @Override
    public boolean editStory(Story story) {

        try {
            CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_STORIES.UPDATE_STORY(?,?,?,?)}");
            cstmt.setInt(1, story.getStoryId());
            cstmt.setString(2, story.getTitle());
            cstmt.setString(3, story.getOverview());
            cstmt.setString(4, story.getContent());

            cstmt.execute();
            log.debug("Story with id: " + story.getStoryId() + " has been updated successfully");
            return true;
        } catch (Exception e) {
            log.error("An error occured during editing story with id: " + story.getStoryId(), e);
            return false;
        }
    }

    @Override
    public boolean deleteStory(Integer storyId) {

        try {

            String sql = "DELETE FROM STORIES WHERE STORY_ID = ?";

            PreparedStatement stmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareStatement(sql);
            stmt.setInt(1, storyId);

            stmt.execute();

            log.debug("Story with id: " + storyId + " has been successfully deleted.");
            return true;
        } catch (Exception e) {
            log.error("Error occured during deleting story with id: " + storyId, e);
            return false;
        }
    }
}
