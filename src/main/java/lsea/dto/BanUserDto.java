package lsea.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Data transfer object for banning a user.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class BanUserDto {

    /**
     * The reason of the ban entry.
     */
    @Size(min = 0, max = 300)
    private String banReason;

    /**
     * The email address of the banned user.
     */
    @NotBlank
    @Pattern(regexp = "^[a-z0-9_.+-]+@[a-z0-9-]+\\.[a-z0-9-.]+$")
    private String emailAddress;
}
