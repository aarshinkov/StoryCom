package com.storycom.beans;

import com.storycom.base.Base;
import com.storycom.security.StoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsersBean extends Base {
    private Logger log = LoggerFactory.getLogger(getClass());

    private StoryUser storyUser;

    public StoryUser getStoryPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (StoryUser) auth.getPrincipal();
    }
}
