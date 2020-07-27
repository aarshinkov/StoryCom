package com.aarshinkov.web.storycom.models.stories;

import java.io.*;
import javax.validation.constraints.*;
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
public class StoryCreateModel implements Serializable
{
  @Size(min = 2, max = 100)
  private String title;

  @NotEmpty
  private String story;

  @NotNull
  @Min(1)
  private Long categoryId;
}
