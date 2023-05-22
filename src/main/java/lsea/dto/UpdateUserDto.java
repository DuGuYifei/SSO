package lsea.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

/**
 * A data transfer object for updating a user.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUserDto {

  /**
   * The username of the user, provided by the user.
   * Must be between 3 and 26 characters long and can only contain letters,
   * underscores and hyphens.
   */
  @NotBlank
  @Size(min = 3, max = 26)
  @Pattern(regexp = "^[a-zA-Z_-]+$")
  private String username;

  /**
   * The email address of the user, provided by the user.
   */
  @NotBlank
  @Email
  private String email;
}
