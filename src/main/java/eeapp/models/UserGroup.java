package eeapp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Store one user group information
 */
@Getter
@Setter
public class UserGroup {

    /**
     * The unique identifier for the user group.
     */
    private UUID id;

    /**
     * The name of the user group.
     */
    private String name;

    /**
     * The description of the user group.
     */
    private String description;

    /**
     * The global access permission level for the user group.
     */
    private int globalPermission;

    /**
     * The timestamp of when the user group was created.
     */
    private Date createdAt;

    /**
     * The timestamp of when the user group was deleted.
     */
    private Date deletedAt;
}
