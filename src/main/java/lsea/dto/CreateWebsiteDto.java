package lsea.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

/**
 * Data transfer object for creating a new user.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CreateWebsiteDto {

  /**
   * The display name of website
   * Must be between 3 and 26 characters long and can only contain letters,
   * underscores and hyphens.
   */
  @NotBlank
  @Size(min = 3, max = 26)
  @Pattern(regexp = "^[a-zA-Z_-]+$")
  private String displayName;

  /**
   * The url of the website
   */
  @NotBlank
  private String redirectUrl;
}
