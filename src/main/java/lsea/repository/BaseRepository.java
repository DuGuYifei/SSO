package lsea.repository;

import java.util.Optional;

/* Requirement 2.10 */
/**
 * The BaseRepository interface provides methods for interacting with data in
 * the system.
 */
public abstract interface BaseRepository {
  /* Requirement 2.9 */
  /**
   * Finds the entity with the given id as string.
   *
   * @param id The id of the entity to find.
   * @return An Optional containing the entity, or empty if the entity was not
   *         found.
   */
  public default Optional<? extends Object> findOne(String id) {
    return Optional.empty();
  }
}
