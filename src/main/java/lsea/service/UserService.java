package lsea.service;

import java.util.*;

import lsea.dto.*;
import lsea.entity.User;
import lsea.errors.GenericConflictError;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.repository.UserRepository;
import lsea.utils.GlobalPermissions;
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
   * @throws GenericConflictError if a user with the same email already exists
   */
  @Transactional
  public void createOne(CreateUserDto dto) throws GenericConflictError {
    Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
    if (existingUser.isPresent()) {
      throw new GenericConflictError(
          "User with email " + dto.getEmail() + " already exists.");
    }
    final User user = User.create(dto);
    userRepository.save(user);
  }

  /**
   * Logs in a user and returns a JWT token.
   *
   * @param dto the user credentials
   * @return a JWT token
   * @throws GenericForbiddenError if the user credentials are invalid
   */
  public String authorize(AuthorizeUserDto dto) throws GenericForbiddenError {
    Optional<User> userOptional = userRepository.findByEmail(dto.getEmail());
    if (!userOptional.isPresent()) {
      throw new GenericForbiddenError("Invalid e-mail or password");
    }

    User user = userOptional.get();

    if (user.isBanned()) {
      throw new GenericForbiddenError("User " + user.getEmail() + " is banned");
    }

    user.verifyPassword(dto.getPassword());
    return user.getJwtToken();
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

  /**
   * Update username of a user by id.
   *
   * @param token the token of the User instance to be updated
   * @param dto   the dto containing the new username and email
   * @throws GenericForbiddenError when user is not authorized
   * @throws GenericNotFoundError  when user is not found
   */
  /* Requirement 7.5 */
  @Transactional
  public void updateOne(String token, UpdateUserDto dto) throws GenericForbiddenError, GenericNotFoundError {
    UUID userId = User.verifyToken(token);
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }
    User user = userOptional.get();
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    userRepository.save(user);
  }

  /**
   * Retrieves a User instance by token.
   *
   * @param token the token of the User instance to be retrieved
   * @return admin instance
   * @throws GenericForbiddenError when user is not authorized
   * @throws GenericNotFoundError  when user is not found
   */
  private User verifyWithPermissions(String token, int globalPermissions)
      throws GenericForbiddenError, GenericNotFoundError {
    UUID userId = User.verifyToken(token);

    Optional<User> userOptional = userRepository.findById(userId);

    if (!userOptional.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }

    User user = userOptional.get();

    if (user.getGlobalPermission() < globalPermissions) {
      throw new GenericForbiddenError("Permission denied");
    }

    return user;
  }

  /**
   * Banning the user by his email address.
   *
   * @param dto   of the user to ban
   * @param token to validate the authorization of the user
   * @throws GenericNotFoundError  when user is not found
   * @throws GenericForbiddenError when user is not authorized
   */
  @Transactional
  public void ban(BanUserDto dto, String token) throws GenericNotFoundError, GenericForbiddenError {
    User user = verifyWithPermissions(token, GlobalPermissions.ADMIN);

    Optional<User> userToBanOptional = userRepository.findByEmail(dto.getEmailAddress());
    if (!userToBanOptional.isPresent()) {
      throw new GenericNotFoundError("User with email " + dto.getEmailAddress() + " does not exist.");
    }

    User userToBan = userToBanOptional.get();

    if (user.equals(userToBan)) {
      throw new GenericForbiddenError("You cannot ban yourself! Ask another admin to do it.");
    }

    userToBan.ban(user.getId(), dto.getBanReason());
    userRepository.save(userToBan);
  }

  /**
   * Unbanning the user by his email address.
   *
   * @param dto   of the user to unban
   * @param token to validate the authorization of the user
   * @throws GenericNotFoundError  when user is not found
   * @throws GenericForbiddenError when user is not authorized
   */
  @Transactional
  public void unBan(UnBanUserDto dto, String token) throws GenericNotFoundError, GenericForbiddenError {
    verifyWithPermissions(token, GlobalPermissions.ADMIN);

    Optional<User> userToUnBanOptional = userRepository.findByEmail(dto.getEmailAddress());
    if (!userToUnBanOptional.isPresent()) {
      throw new GenericNotFoundError("User with email " + dto.getEmailAddress() + " does not exist.");
    }

    User user = userToUnBanOptional.get();

    user.unBan();
    userRepository.save(user);
  }
}
