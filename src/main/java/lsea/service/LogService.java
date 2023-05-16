package lsea.service;

import java.sql.Timestamp;
import java.util.List;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service layer for all business actions regarding Log entity.
 */
@Service
public class LogService {

  /**
   * The log repository.
   */
  private final LogRepository logRepository;

  /**
   * The user repository.
   */
  private final UserRepository userRepository;

  /**
   * Constructor of the LogService class.
   *
   * @param logRepository  - LogRepository
   * @param userRepository - UserRepository
   */
  public LogService(
      LogRepository logRepository,
      UserRepository userRepository) {
    this.logRepository = logRepository;
    this.userRepository = userRepository;
  }

  /**
   * Creates a new log.
   *
   * @param dto   options from user input
   * @param token to validate the authorization of the user
   * @return the created log
   * @throws GenericNotFoundError  when user is not found
   * @throws GenericForbiddenError when user is not authorized
   */
  @Transactional
  public Log createOne(CreateLogDto dto, String token)
      throws GenericNotFoundError, GenericForbiddenError {
    UUID userId = User.verifyToken(token);

    Optional<User> userOptional = userRepository.findById(userId);

    if (!userOptional.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }

    User user = userOptional.get();

    if (user.isBanned()) {
      throw new GenericForbiddenError("User " + user.getEmail() + " is banned");
    }

    Log log = Log.create(dto, user);
    return logRepository.save(log);
  }

  /**
   * Returns a list of logs.
   *
   * @param offset - the offset
   * @param limit  - the limit
   * @return a list of logs
   */
  public List<Log> findLogs(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset, limit);
    return logRepository.findLogs(pageable);
  }

  /**
   * Returns a list of logs that were created after a given timestamp.
   *
   * @param timestamp - Timestamp
   * @return a list of logs
   */
  public List<Log> findLiveLogs(Timestamp timestamp) {
    return logRepository.findLiveLogs(timestamp);
  }
}
