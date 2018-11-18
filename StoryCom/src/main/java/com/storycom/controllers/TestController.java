package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Story;
import com.storycom.repository.StoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping(value = "/test")
public class TestController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private StoriesRepository storiesRepository;

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

}
