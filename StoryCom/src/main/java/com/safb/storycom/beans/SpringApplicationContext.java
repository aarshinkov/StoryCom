package com.safb.storycom.beans;

import org.springframework.beans.*;
import org.springframework.context.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 3.0.1
 */
public class SpringApplicationContext
{
  private static ApplicationContext CONTEXT;

  public void setApplicationContext(ApplicationContext context) throws BeansException
  {
    CONTEXT = context;
  }

  public static Object getBean(String beanName)
  {
    return CONTEXT.getBean(beanName);
  }
}
