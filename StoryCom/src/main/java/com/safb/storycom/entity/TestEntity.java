package com.safb.storycom.entity;

import java.io.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test")
public class TestEntity implements Serializable
{
  @Id
  @SequenceGenerator(name = "seq_gen_test", sequenceName = "s_test", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_test")
  @Column(name = "test_id")
  private Integer testId;

  @Column(name = "test_name")
  private String testName;

  @Column(name = "test_one")
  private String testOne;

  @Column(name = "test_two")
  private String testTwo;
}
