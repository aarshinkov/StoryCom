package com.storycom.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/story")
public class StoriesController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/add")
    public String prepareAddStory() {
        log.debug("prepareAddStory() begin ---");

        return "stories/addStory";
    }

}
