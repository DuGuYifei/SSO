package lsea.utils;

/* @Requirement-3.5 */
/**
 * The possible global permissions that a user can have.
 * These permissions can be used to restrict access to certain parts of the application.
 */
public enum GlobalPermissions {

    /**
     * Indicates that a user has administrator-level permissions.
     */
    ADMIN(99),

    /**
     * Indicates that a user has moderator-level permissions.
     */
    MODERATOR(50),

    /**
     * Indicates that a user has basic user-level permissions.
     */
    USER(0);

    /**
     * The value of the permission.
     */
    private final int value;

    /**
     * Creates a new permission with the given value.
     * 
     * @param value The numerical value of the permission.
     */
    GlobalPermissions(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value of the permission.
     * 
     * @return The numerical value of the permission.
     */
    public int getValue() {
        return value;
    }
}
