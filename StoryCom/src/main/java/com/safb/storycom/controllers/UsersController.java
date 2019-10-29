package com.safb.storycom.controllers;

import com.safb.storycom.base.Base;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UsersController extends Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

}
