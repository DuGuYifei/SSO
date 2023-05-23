package lsea.repository;

import java.util.Optional;
import java.util.UUID;
import lsea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The UserRepository interface provides methods for interacting with user data
 * in the system.
 */
@Repository
public interface UserRepository
    extends JpaRepository<User, UUID>, BaseRepository {
  /**
   * Finds the user with the given username.
   *
   * @param username The username of the user to find.
   * @return An Optional containing the user, or empty if the user was not found.
   */
  /* Requirement 7.4 */
  /*
   * Based on
   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query
   * -methods.query-creation,
   * the following method declaration, under the hood works as a parametrized
   * query like: "where x.username = ?1"
   */
  Optional<User> findByUsername(String username);

  /**
   * Finds the user with the given email address.
   *
   * @param email The email address of the user to find.
   * @return An Optional containing the user, or empty if the user was not found.
   */
  /* Requirement 7.4 */
  /*
   * Based on
   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query
   * -methods.query-creation,
   * the following method declaration, under the hood works as a parametrized
   * query like: "where x.email = ?1"
   */
  Optional<User> findByEmail(String email);
}
