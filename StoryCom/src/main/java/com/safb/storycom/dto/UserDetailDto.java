package com.safb.storycom.dto;

import lombok.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 3.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto
{
  private Integer userDetailId;
  private String gender;
  private CountryDto country;
  private String facebook;
  private String twitter;
  private String youtube;
  private String instagram;
}
