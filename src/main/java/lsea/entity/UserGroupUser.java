package lsea.entity;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import lsea.dto.AddUserToUserGroupDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GroupPermissions;

/* Requirement 7.1 */
/**
 * Entity class representing the relationship between a user and a user group.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "usergroup_user")
public class UserGroupUser {

  /**
   * The unique identifier for this relationship.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @SerializedName("id")
  private UUID id;

  /**
   * The ID of the user group.
   */
  @Column(name = "user_group_id")
  @SerializedName("userGroupId")
  private UUID userGroupId;

  /**
   * The ID of the user.
   */
  @Column(name = "user_id")
  @SerializedName("userId")
  private UUID userId;

  /**
   * The date when the user was added to the group.
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "added_at")
  @SerializedName("addedAt")
  private Date addedAt;

  /**
   * The permissions of the user within the group.
   */
  @Column(name = "group_permission")
  @SerializedName("groupPermission")
  private GroupPermissions groupPermission;

  /**
   * Creates a new UserGroupUser instance based on the given DTO.
   *
   * @param dto       the DTO containing the information to use for the new
   *                  instance
   * @param adderUser the user adding the new user to the group
   * @return the new UserGroupUser instance
   * @throws GenericForbiddenError if the user adding the new user to the group
   *                               and doesn't have permissions to do so
   */
  public static UserGroupUser create(
      AddUserToUserGroupDto dto,
      UserGroupUser adderUser) throws GenericForbiddenError {
    if (adderUser.groupPermission.equals(GroupPermissions.SPECTATOR)) {
      throw new GenericForbiddenError(
          "The user adding the new user to the group must have a permission level greater than SPECTATOR.");
    }

    if (adderUser.groupPermission.ordinal() < dto.getRole()) {
      throw new GenericForbiddenError(
          "The user adding the new user to the group must have a permission level greater or equal than the new user.");
    }

    return UserGroupUser
        .builder()
        .id(UUID.randomUUID())
        .userGroupId(UUID.fromString(dto.getUserGroupId()))
        .userId(UUID.fromString(dto.getUserId()))
        .addedAt(new Date())
        .groupPermission(GroupPermissions.values()[dto.getRole()])
        .build();
  }
}
