package eeapp.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Store one user information
 */
@Getter
@Setter
public class User {

    /**
     * Unique identifier for the user.
     */
    private UUID id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The encrypted password of the user.
     */
    private String password;

    /**
     * The date the user was created.
     */
    private Date createdAt;

    /**
     * The date the user was last updated.
     */
    private Date updatedAt;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The confirmation code of the user.
     */
    private String confirmationCode;

    /**
     * The date the user was confirmed.
     */
    private Date confirmedAt;

    /**
     * The global permission level of the user.
     */
    private int globalPermission;

    /**
     * The date the user was banned.
     */
    private Date bannedAt;

    /**
     * The reason the user was banned.
     */
    private String banReason;

    /**
     * The ID of the admin who banned this user.
     */
    private UUID bannedById;

}
