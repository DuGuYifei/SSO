package lsea.entity;

import lombok.SneakyThrows;
import lsea.LaboratoryApplication;
import lsea.dto.CreateUserDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GlobalPermissions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

/**
 * Unit tests for the User class.
 */
@SpringBootTest(classes = { LaboratoryApplication.class })
@Transactional
public class UserTest {

    /**
     * The user object.
     */
    private User user;

    /**
     * Set up the test data before each test case.
     */
    @BeforeEach
    public void setUp() {
        user = User.create(CreateUserDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build());
    }

    /**
     * Test the creation of a new User instance based on CreateUserDto.
     */
    @Test
    @Rollback
    public void testCreate() {
        CreateUserDto dto = CreateUserDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build();

        User createdUser = User.create(dto);

        Assertions.assertEquals(dto.getUsername(), createdUser.getUsername());
        Assertions.assertEquals(dto.getEmail(), createdUser.getEmail());
        Assertions.assertEquals(true, BCrypt.checkpw(dto.getPassword(), createdUser.getPassword()));
    }

    /**
     * Test the ban method of User.
     */
    @Test
    @Rollback
    public void testBan() {
        UUID adminId = UUID.randomUUID();
        String banReason = "Violated community guidelines";

        user.ban(adminId, banReason);

        Assertions.assertNotNull(user.getBannedAt());
        Assertions.assertEquals(adminId, user.getBannedById());
        Assertions.assertEquals(banReason, user.getBanReason());
    }

    /**
     * Test the unBan method of User.
     */
    @Test
    @Rollback
    public void testUnBan() {
        user.unBan();

        Assertions.assertNull(user.getBannedAt());
        Assertions.assertNull(user.getBannedById());
        Assertions.assertNull(user.getBanReason());
    }

    /**
     * Test the isBanned method of User.
     */
    @Test
    @Rollback
    public void testIsBanned() {
        user.ban(UUID.randomUUID(), "Violated community guidelines");

        Assertions.assertTrue(user.isBanned());
    }

    /**
     * Test changing the password of User.
     */
    @SneakyThrows
    @Test
    @Rollback
    public void testChangePassword() {
        String oldPassword = "password";
        String newPassword = "newpassword";

        user.changePassword(oldPassword, newPassword);

        Assertions.assertThrows(GenericForbiddenError.class, () -> user.verifyPassword(oldPassword));
    }

    /**
     * Test verifying the password of User.
     */
    @Test
    @Rollback
    public void testVerifyPassword() {
        String correctPassword = "password";
        String incorrectPassword = "incorrect";

        Assertions.assertDoesNotThrow(() -> user.verifyPassword(correctPassword));
        Assertions.assertThrows(GenericForbiddenError.class, () -> user.verifyPassword(incorrectPassword));
    }

    /**
     * Test generating a JWT token for User.
     */
    @Test
    @Rollback
    public void testGetJwtToken() {
        String jwtToken = user.getJwtToken();
        Assertions.assertNotNull(jwtToken);
    }

    /**
     * Test converting User object to JSON.
     */
    @Test
    @Rollback
    public void testToJson() {
        String json = user.toJson();
        Assertions.assertNotNull(json);
        Assertions.assertEquals(false, json.contains("password"));
    }

    /**
     * Test verifying a JWT token and getting the user ID.
     */
    @Test
    @Rollback
    public void testVerifyToken() {
        String jwtToken = user.getJwtToken();

        Assertions.assertDoesNotThrow(() -> {
            UUID userId = User.verifyToken(jwtToken);
            Assertions.assertEquals(user.getId(), userId);
        });

        Assertions.assertThrows(GenericForbiddenError.class, () -> User.verifyToken("invalid_token"));
    }
}
