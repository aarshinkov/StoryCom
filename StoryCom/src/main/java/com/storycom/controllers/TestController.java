package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.entity.Story;
import com.storycom.repository.StoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.Media;
import java.util.List;

@Controller
@RequestMapping(value = "/test")
public class TestController extends Base {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private StoriesRepository storiesRepository;

    @ResponseBody
    @RequestMapping(value = "/story/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Story storyById(@PathVariable("id") Integer storyId) {
        return storiesRepository.findByStoryId(storyId);
    }
}
