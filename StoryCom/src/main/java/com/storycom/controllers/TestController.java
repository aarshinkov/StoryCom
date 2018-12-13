package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Story;
import com.storycom.entity.User;
import com.storycom.repository.StoriesRepository;
import com.storycom.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/test")
public class TestController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private StoriesRepository storiesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/design")
    public String designTest() {
        log.debug("Design test");

        return "test/designTest";
    }

    @GetMapping(value = "/design/story")
    public String designStory(@RequestParam(name = "id") Integer storyId, Model model) {
        log.debug("Design story");

        Story story = storiesRepository.findByStoryId(storyId);

        model.addAttribute("story", story);

        return "test/designTest";
    }

    @ResponseBody
    @GetMapping(value = "/story/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Story storyById(@PathVariable("id") Integer storyId) {
        log.debug("storyById() begin --");
        return storiesRepository.findByStoryId(storyId);
    }

    @ResponseBody
    @GetMapping(value = "/story", produces = MediaType.APPLICATION_JSON_VALUE)
    public Story storyByIdParam(@RequestParam(name = "id") Integer storyId) {
        log.debug("storyByIdParam() begin --");
        return storiesRepository.findByStoryId(storyId);
    }

    @ResponseBody
    @GetMapping(value = "/encoding", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testEncoding() {
        log.debug("testEncoding() begin --");

        String word = "Здравейте, приятели";

        log.debug("word: " + word);

        return word;
    }

    @ResponseBody
    @GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser() {
        return usersRepository.findByUserId(getStoryUser().getUserId());
    }

    @ResponseBody
    @GetMapping(value = "/encodePass/{pass}")
    public String encodePass(@PathVariable(value = "pass") String password) {
        return passwordEncoder.encode(password);
    }

    @ResponseBody
    @GetMapping(value = "/password/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPasswordMatch(@PathVariable("password") String password) {
        log.debug("password: " + password);

        String sql = "SELECT U.PASSWORD FROM USERS U WHERE U.USER_ID = ?";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, 1001);

        String dbPassword = "-1";

        while (rs.next()) {
            dbPassword = rs.getString(1);
        }

        log.debug("dbPassword: " + dbPassword);

        return passwordEncoder.matches(password, dbPassword);
    }

}
