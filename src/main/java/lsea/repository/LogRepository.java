package lsea.repository;

import lsea.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The log interface provides methods for interacting with log data in the
 * system.
 */
@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {
}
