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

  @Column(name = "test_name")
  private String testName;
  
  @Column(name = "test_one")
  private String testOne;
  
  @Column(name = "test_two")
  private String testTwo;

  @Override
  public String toString()
  {
    return "Test{" + "testId=" + testId + ", testName=" + testName + ", testOne=" + testOne + ", testTwo=" + testTwo + '}';
  }

  public Integer getTestId()
  {
    return testId;
  }

  public void setTestId(Integer testId)
  {
    this.testId = testId;
  }

  public String getTestName()
  {
    return testName;
  }

  public void setTestName(String testName)
  {
    this.testName = testName;
  }

  public String getTestOne()
  {
    return testOne;
  }

  public void setTestOne(String testOne)
  {
    this.testOne = testOne;
  }

  public String getTestTwo()
  {
    return testTwo;
  }

  public void setTestTwo(String testTwo)
  {
    this.testTwo = testTwo;
  }
}
