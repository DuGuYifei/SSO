package lsea.tcp;

import lsea.entity.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * This class contains the tests for the TCP server functions.
 */
public class TCPServerFunctionsTest {

    /**
     * No logs for a random user id, so the list should be empty.
     */
    @Test
    void testGetLogsFromDatabase() {
        UUID userId = UUID.randomUUID();
        List<Log> logs = TCPServerFunctions.getLogsFromDatabase(userId, 10, 0);
        Assertions.assertEquals(0, logs.size());
    }

    /**
     * No new logs for a random user id, so the list should be empty.
     */
    @Test
    void testGetNewLogsFromDatabase() {
        UUID userId = UUID.randomUUID();
        Timestamp timestamp = Timestamp.from(Instant.now());
        List<Log> newLogs = TCPServerFunctions.getNewLogsFromDatabase(userId, timestamp);
        Assertions.assertTrue(newLogs.isEmpty());
    }
}
