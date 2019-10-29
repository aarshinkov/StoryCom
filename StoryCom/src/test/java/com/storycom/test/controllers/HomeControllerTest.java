package com.storycom.test.controllers;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.web.context.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{
  "classpath:spring/mvc-core-config.xml", "classpath:spring/resource-config.xml", "classpath*:spring/security-config.xml"
})
@WebAppConfiguration
public class HomeControllerTest
{
  @Autowired
  private WebApplicationContext wac;

  private MockMvc mock;

  @Before
  public void setup()
  {
    this.mock = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void testHomePage() throws Exception
  {
    this.mock.perform(get(""))
            .andExpect(status().isOk())
            .andExpect(view().name("home/home"))
            .andExpect(model().attribute("submenu", "home"))
            .andReturn();
  }
}
