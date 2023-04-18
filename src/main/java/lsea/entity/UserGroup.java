package lsea.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import lsea.dto.CreateUserGroupDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GlobalPermissions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/* Requirement 2.1 */
/**
 * Represents a user group entity in the system.
 * Extends PermissionedEntity to allow for checking of global access
 * permissions.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "usergroups")
public class UserGroup extends PermissionedEntity implements Serializable {

    /**
     * The unique identifier for the user group.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("id")
    private UUID id;

    /**
     * The name of the user group.
     */
    @SerializedName("name")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * The description of the user group.
     */
    @SerializedName("description")
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * The global access permission level for the user group.
     */
    @Column(name = "global_permission", nullable = false)
    private int globalPermission;

    /**
     * The timestamp of when the user group was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("created_at")
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    /**
     * The timestamp of when the user group was deleted.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("deleted_at")
    @Column(name = "deleted_at")
    private Date deletedAt;

    /**
     * Creates a new user group entity from a CreateUserGroupDto and a User creator.
     * Only users with the MODERATOR global access permission level or higher are
     * allowed to create user groups.
     * 
     * @param dto     the CreateUserGroupDto object containing the data for the new
     *                user group
     * @param creator the User object of the creator of the new user group
     * @return the new UserGroup object
     * @throws GenericForbiddenError if the creator does not have sufficient global
     *                               access permissions to create a user group
     */
    public static UserGroup create(CreateUserGroupDto dto, User creator) throws GenericForbiddenError {
        if (!creator.hasGlobalAccess(GlobalPermissions.MODERATOR)) {
            throw new GenericForbiddenError("You are not authorized to create a user group");
        }
        return UserGroup.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .description(dto.getDescription())
                .globalPermission(GlobalPermissions.USER)
                .createdAt(new Date())
                .build();
    }

    /**
     * Converts the UserGroup object to a JSON string.
     * 
     * @return the JSON string representation of the UserGroup object
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
