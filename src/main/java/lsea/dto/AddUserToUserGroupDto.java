package lsea.dto;

import lombok.*;

import javax.validation.constraints.*;

/**
 * A DTO class representing a request to add a user to a user group.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class AddUserToUserGroupDto {

  /**
   * Unique identifier of the group of users
   */
  @NotBlank
  @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$", message = "Invalid user group id")
  private String userGroupId;

  /**
   * Id of the user in the group
   */
  @NotBlank
  @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$", message = "Invalid user id")
  private String userId;

  /**
   * Role of the added user in the group
   */
  @NotBlank
  @Min(0)
  @Max(4)
  private int role;
}
