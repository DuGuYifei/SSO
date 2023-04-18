package lsea.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) representing the information needed to create a
 * new user group in the SSO system.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CreateUserGroupDto {

    /**
     * The name of the new user group.
     * Must be between 3 and 25 characters, alphanumeric and without spaces.
     */
    @NotBlank
    @Size(min = 3, max = 25, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name must be alphanumeric with no spaces")
    private String name;

    /**
     * A description of the new user group.
     * Must be between 0 and 500 characters.
     */
    @NotBlank
    @Size(min = 0, max = 500, message = "Description must be between 0 and 500 characters")
    private String description;
}
