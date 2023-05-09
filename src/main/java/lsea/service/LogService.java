package lsea.service;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lsea.dto.CreateLogDto;
import lsea.entity.Log;
import lsea.entity.User;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.repository.LogRepository;
import lsea.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer for all business actions regarding Log entity.
 */
@Service
public class LogService {

  private final LogRepository logRepository;

  private final UserRepository userRepository;

  /**
   * Constructor of the LogService class.
   *
   * @param logRepository - LogRepository
   * @param userRepository - UserRepository
   */
  public LogService(
    LogRepository logRepository,
    UserRepository userRepository
  ) {
    this.logRepository = logRepository;
    this.userRepository = userRepository;
  }

  /**
   * Creates a new log.
   *
   * @param dto options from user input
   * @param token to validate the authorization of the user
   * @return the created log
   * @throws GenericNotFoundError when user is not found
   * @throws GenericForbiddenError when user is not authorized
   */
  @Transactional
  public Log createOne(CreateLogDto dto, String token)
    throws GenericNotFoundError, GenericForbiddenError {
    UUID userId = User.verifyToken(token);

    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }

    Log log = Log.create(dto, user.get());
    return logRepository.save(log);
  }
}
