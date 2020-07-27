package com.aarshinkov.web.storycom.enums;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
public enum Categories
{
  LOVE(1),
  TEEN(2),
  FAMILY(3),
  HEALTH(4),
  EDUCATION(5);

  private final Long value;

  private Categories(long value)
  {
    this.value = value;
  }

  public Long getValue()
  {
    return value;
  }
}
