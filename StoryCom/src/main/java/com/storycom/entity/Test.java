package com.storycom.entity;

import javax.persistence.*;

@Entity
@Table(name = "TEST")
public class Test
{
  @Id
  @SequenceGenerator(name = "SEQ_GEN_TEST", sequenceName = "S_TEST", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_TEST")
  @Column(name = "test_id")
  private Integer testId;
  
  @Column(name = "text")
  private String text;

  public Integer getTestId()
  {
    return testId;
  }

  public void setTestId(Integer testId)
  {
    this.testId = testId;
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }
  
  

}
