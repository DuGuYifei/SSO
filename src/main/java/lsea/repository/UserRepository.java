package lsea.repository;

import lsea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The UserRepository interface provides methods for interacting with user data in the system.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds the user with the given username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the user, or empty if the user was not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds the user with the given email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the user, or empty if the user was not found.
     */
    Optional<User> findByEmail(String email);
}
