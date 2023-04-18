package lsea.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * A data transfer object for updating a user's password.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
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
