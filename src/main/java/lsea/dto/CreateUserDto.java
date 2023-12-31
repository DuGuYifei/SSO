package lsea.dto;

import javax.validation.constraints.*;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserDto {

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
   * The password of the user, provided by the user.
   * Must be up to 100 characters long and at least 8 characters long.
   */
  @NotBlank
  @Size(min = 8, max = 100)
  private String password;

  /**
   * The email address of the user, provided by the user.
   */
  @NotBlank
  @Pattern(regexp = "^[a-z0-9_.+-]+@[a-z0-9-]+\\.[a-z0-9-.]+$")
  private String email;
}
