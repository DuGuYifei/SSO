package lsea.service;

import java.util.*;
import lsea.dto.AuthorizeUserDto;
import lsea.dto.CreateUserDto;
import lsea.entity.User;
import lsea.errors.GenericConflictError;
import lsea.errors.GenericForbiddenError;
import lsea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for all business actions regarding User entity.
 */
@Service
public class UserService extends BaseService {

  /**
   * Repository for user management.
   */
  private UserRepository userRepository;

  /**
   * Constructs a new instance of UserService class with UserRepositoryInterface
   * dependency injection.
   *
   * @param userRepository an instance of UserRepositoryInterface
   */
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /* Requirement 2.8 */
  /**
   * Default constructor.
   */
  public UserService() {
    System.out.println("Initializing user service...");
  }

  /**
   * Saves a new User to the UserRepository.
   *
   * @param dto a new User instance
   * @throws Exception if a user with the same email address already exists in the
   *                   UserRepository
   */
  @Transactional
  public void createOne(CreateUserDto dto) throws Exception {
    Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
    if (existingUser.isPresent()) {
      throw new GenericConflictError(
        "User with email " + dto.getEmail() + " already exists."
      );
    }
    final User user = User.create(dto);
    userRepository.save(user);
  }

  /**
   * Logs in a user and returns a JWT token.
   *
   * @param dto the user credentials
   * @return a JWT token
   * @throws Exception if the user credentials are invalid
   */
  public String authorize(AuthorizeUserDto dto) throws Exception {
    Optional<User> user = userRepository.findByEmail(dto.getEmail());
    if (!user.isPresent()) {
      throw new GenericForbiddenError("Invalid e-mail or password");
    }
    user.get().verifyPassword(dto.getPassword());
    return user.get().getJwtToken();
  }

  /**
   * Retrieves a User instance by id.
   *
   * @param id the id of the User instance to be retrieved
   * @return an Optional containing the User instance if it exists, otherwise
   *         returns an empty Optional
   */
  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  }
}
