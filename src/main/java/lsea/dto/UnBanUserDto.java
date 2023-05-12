package lsea.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Data transfer object for unbanning a user.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UnBanUserDto {

    /**
     * The email address of the unbanned user.
     */
    @NotBlank
    @Pattern(regexp = "^[a-z0-9_.+-]+@[a-z0-9-]+\\.[a-z0-9-.]+$")
    private String emailAddress;
}
