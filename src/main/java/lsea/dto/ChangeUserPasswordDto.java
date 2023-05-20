package lsea.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;

/**
 * A data transfer object for updating a user's password.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeUserPasswordDto {

  /**
   * The password of the user, provided by the user.
   * Must be up to 73 characters long.
   */
  @NotBlank
  @Size(max = 73)
  private String password;

  /**
   * The new password of the user, provided by the user.
   * Must be up to 73 characters long.
   */
  @NotBlank
  @Size(max = 73)
  private String newPassword;
}
