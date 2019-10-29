package com.storycom.test.controllers;

import com.safb.storycom.controllers.HomeController;
import org.junit.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.result.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest
{
  private MockMvc mockMvc;

  @Before
  public void setup()
  {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
  }

  @Test
  public void testHomePage() throws Exception
  {
    this.mockMvc.perform(get(""))
            .andExpect(status().isOk())
            .andExpect(view().name("home/home"))
            .andExpect(model().attribute("submenu", "home"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
  }
}
