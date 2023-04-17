package lsea.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/* @Requirement-2.1 */
/**
 * Data transfer object for creating a new user.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CreateUserDto {

    /**
     * The username of the user, provided by the user.
     * Must be between 3 and 26 characters long and can only contain letters, underscores and hyphens.
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
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;
}
