package lsea.entity;

/* Requirement 2.10 */
/**
 * Abstract class representing an entity that has a permission level.
 * This class provides a method for checking whether the entity has global
 * access to a given permission level.
 * A subclass of PermissionedEntity can use this method to implement its own
 * access control logic based on the entity's permission level.
 */
public abstract class PermissionedEntity {

  /**
   * The permission level of the entity.
   */
  private int permission;

  /**
   * Checks whether the entity has global access to a given permission level.
   *
   * @param permission the permission level to check for access
   * @return true if the entity has global access to the given permission level,
   *         false otherwise
   */
  public boolean hasGlobalAccess(int permission) {
    return this.permission >= permission;
  }
}
