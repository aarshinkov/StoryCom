package com.safb.storycom.entity;

import java.io.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TEST")
public class Test implements Serializable
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
}
