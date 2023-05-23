package lsea.service;

import java.sql.Timestamp;
import java.util.*;
import javax.transaction.Transactional;

import lsea.controllers.ValidationRouter;
import lsea.dto.CreateLogDto;
import lsea.entity.Log;
import lsea.entity.User;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.repository.LogRepository;
import lsea.repository.UserRepository;
import lsea.utils.GlobalPermissions;
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
  /* Requirement 7.2 */
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

  /* Requirement 7.7 */
  /**
   * Returns the map with the database performance tests results.
   *
   * @param logsNumber - the number of logs to be generated
   * @param token - the token of the user
   * @return the map with the database performance tests results
   * @throws GenericForbiddenError when user is not authorized
   * @throws GenericNotFoundError  when user is not found
   */
  public Map<String, Object> generateTestData(int logsNumber, String token) throws GenericForbiddenError, GenericNotFoundError {
    List<Long> durationsWrite = new ArrayList<>();
    List<Long> durationsRead = new ArrayList<>();
    List<Log> logs = new ArrayList<>();

    UUID userId = User.verifyToken(token);

    Optional<User> userOptional = userRepository.findById(userId);

    if (!userOptional.isPresent()) {
      throw new GenericNotFoundError("User not found");
    }

    User user = userOptional.get();

    if (user.getGlobalPermission() < GlobalPermissions.ADMIN) {
      throw new GenericForbiddenError("Permission denied");
    }

    long memoryBeforeWriteTest = getUsedMemory();

    for (int i = 0; i < logsNumber; i++) {
      CreateLogDto dto = CreateLogDto.builder()
        .data("test" + i)
        .logType(0)
        .build();

      Log log = Log.create(dto, user);
      logs.add(log);

      long startTime = System.currentTimeMillis();
      logRepository.save(log);
      long endTime = System.currentTimeMillis();
      long duration = endTime - startTime;
      durationsWrite.add(duration);
    }

    long memoryAfterWriteTest = getUsedMemory();
    float writeMemoryUsage = (float) (memoryAfterWriteTest - memoryBeforeWriteTest) / 1024;

    for (Log log : logs) {
      long startTime = System.currentTimeMillis();
      logRepository.findById(log.getId());
      long endTime = System.currentTimeMillis();
      long duration = endTime - startTime;
      durationsRead.add(duration);
    }

    long totalDurationWrite = durationsWrite.stream().mapToLong(Long::longValue).sum();
    float averageDurationWrite = (float) totalDurationWrite / logsNumber;

    long totalDurationRead = durationsRead.stream().mapToLong(Long::longValue).sum();
    float averageDurationRead = (float) totalDurationRead / logsNumber;

    List<Long> sortedDurationsWrite = new ArrayList<>(durationsWrite);
    List<Long> sortedDurationsRead = new ArrayList<>(durationsRead);
    Collections.sort(sortedDurationsWrite);
    Collections.sort(sortedDurationsRead);

    long medianDurationWrite = sortedDurationsWrite.get(logsNumber / 2);
    long medianDurationRead = sortedDurationsRead.get(logsNumber / 2);

    double sumOfSquaredDeviationsWrite = 0;
    double sumOfSquaredDeviationsRead = 0;

    for (long duration : durationsWrite) {
      double deviation = duration - averageDurationWrite;
      sumOfSquaredDeviationsWrite += deviation * deviation;
    }

    for (long duration : durationsRead) {
      double deviation = duration - averageDurationRead;
      sumOfSquaredDeviationsRead += deviation * deviation;
    }

    double standardDeviationWrite = Math.sqrt(sumOfSquaredDeviationsWrite / logsNumber);
    double standardDeviationRead = Math.sqrt(sumOfSquaredDeviationsRead / logsNumber);

    Map<String, Object> results = new HashMap<>();
    results.put("totalDurationWrite", totalDurationWrite);
    results.put("totalDurationRead", totalDurationRead);
    results.put("averageDurationWrite", averageDurationWrite);
    results.put("averageDurationRead", averageDurationRead);
    results.put("medianDurationWrite", medianDurationWrite);
    results.put("medianDurationRead", medianDurationRead);
    results.put("standardDeviationWrite", standardDeviationWrite);
    results.put("standardDeviationRead", standardDeviationRead);
    results.put("writeMemoryUsage (KB)", writeMemoryUsage);

    return results;
  }

  /**
   * Returns the used memory.
   *
   * @return the used memory
   */
  private long getUsedMemory() {
    Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory() - runtime.freeMemory();
  }
}
