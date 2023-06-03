package lsea.entity;

import lsea.LaboratoryApplication;
import lsea.dto.CreateLogDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.LogType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the Log class.
 */
@SpringBootTest(classes = { LaboratoryApplication.class })
public class LogTest {

    /**
     * The user object.
     */
    private User user;

    /**
     * Set up the test data before each test case.
     */
    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(UUID.randomUUID());
    }

    /**
     * Test the creation of a new log entry using the Log create method.
     */
    @Test
    public void testCreateLog() throws GenericForbiddenError {
        CreateLogDto createLogDto = CreateLogDto.builder()
                .data("Test Log Data")
                .logType(LogType.Success.ordinal())
                .build();

        Log log = Log.create(createLogDto, user);

        assertEquals(createLogDto.getData(), log.getData());
        assertEquals(LogType.Success, log.getLogType());
        assertEquals(user.getId(), log.getUserId());
        assertNotNull(log.getCreatedAt());
        assertEquals(user.toJson(), log.getUserCurrentState());
    }

    /**
     * Test the creation of a new log entry with an invalid log type.
     */
    @Test
    public void testCreateLogWithInvalidLogType() {
        CreateLogDto createLogDto = CreateLogDto.builder()
                .data("Test Log Data")
                .logType(10) // Invalid log type
                .build();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            Log.create(createLogDto, user);
        });
    }
}
