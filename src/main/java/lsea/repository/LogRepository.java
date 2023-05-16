package lsea.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lsea.entity.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The log interface provides methods for interacting with log data in the
 * system.
 */
@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {

    /**
     * Find all logs for a given user.
     *
     * @param userId - the user's id
     * @param pageable - the page
     * @return a list of logs for the user
     */
    @Query("SELECT l FROM Log l WHERE l.userId = ?1 ORDER BY l.createdAt DESC")
    List<Log> findUserLogs(UUID userId, Pageable pageable);

    /**
     * Find all logs for a given user that were created after a given timestamp.
     *
     * @param userId - the user's id
     * @param timestamp - the timestamp
     * @return a list of logs for the user
     */
    @Query("SELECT l FROM Log l WHERE l.userId = ?1 AND l.createdAt > ?2 ORDER BY l.createdAt DESC")
    List<Log> findUserLiveLogs(UUID userId, Timestamp timestamp);
}
