package lsea.utils;

/* Requirement 3.5 */
/**
 * Utility enum class representing the possible permission levels for user
 * groups in the SSO system.
 */
public enum GroupPermissions {

    /**
     * SPECTATOR: a user with spectator permission can only view the content of the
     * group.
     */
    SPECTATOR,

    /**
     * REGULAR: a regular user can view and contribute to the group's content.
     */
    REGULAR,

    /**
     * MODERATOR: a moderator user has the same permissions as a regular user, but
     * can also moderate content and manage members.
     */
    MODERATOR,

    /**
     * ADMIN: an admin user has all the permissions of a moderator, as well as the
     * ability to manage the group itself (e.g. change its name or description).
     */
    ADMIN,

    /**
     * OWNER: the owner of a group has all the permissions of an admin, as well as
     * the ability to transfer ownership of the group to another user.
     */
    OWNER;
}
