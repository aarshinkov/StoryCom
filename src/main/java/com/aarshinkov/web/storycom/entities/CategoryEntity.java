package com.aarshinkov.web.storycom.entities;

import java.io.*;
import java.sql.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "categories")
//@DynamicInsert
//@DynamicUpdate
public class CategoryEntity implements Serializable
{
  @Id
  @SequenceGenerator(name = "seq_gen_category", sequenceName = "s_categories", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_category")
  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "name")
  private String name;

  @Column(name = "created_on")
  private Timestamp createdOn;
}
