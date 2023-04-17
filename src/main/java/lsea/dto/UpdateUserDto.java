package lsea.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/* @Requirement-2.1 */
/**
 * A data transfer object for updating a user.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class UpdateUserDto {

    /**
     * The username of the user, provided by the user.
     * Must be between 3 and 26 characters long and can only contain letters, underscores and hyphens.
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
