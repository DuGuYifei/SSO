package lsea.tcp;

import lsea.LaboratoryApplication;
import lsea.entity.Log;
import lsea.service.LogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * This class contains the tests for the TCP server functions.
 */
@SpringBootTest(classes = { LaboratoryApplication.class })
public class TCPServerFunctionsTest {

    /**
     * The log service.
     */
    @Autowired
    private LogService logService;

     /**
     * No logs for a random user id, so the list should be empty.
     */
    @Test
    void testFindUserLogs() {
        UUID userId = UUID.randomUUID();
        List<Log> logs = logService.findUserLogs(userId, 0, 10);
        Assertions.assertEquals(0, logs.size());
    }

    /**
     * No new logs for a random user id, so the list should be empty.
     */
    @Test
    void testFindUserLiveLogs() {
        UUID userId = UUID.randomUUID();
        Timestamp timestamp = Timestamp.from(Instant.now());
        List<Log> newLogs = logService.findUserLiveLogs(userId, timestamp);
        Assertions.assertTrue(newLogs.isEmpty());
    }
}
