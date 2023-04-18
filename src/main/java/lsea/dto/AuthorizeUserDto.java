package lsea.dto;

import javax.validation.constraints.*;

import lombok.*;

/**
 * Data transfer object for authorizing a user.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class AuthorizeUserDto {

    /**
     * The email address of the user, provided by the user.
     */
    @NotBlank
    @Pattern(regexp = "^[a-z0-9_.+-]+@[a-z0-9-]+\\.[a-z0-9-.]+$")
    private String email;

    /**
     * The password of the user, provided by the user.
     */
    @NotBlank
    private String password;
}
