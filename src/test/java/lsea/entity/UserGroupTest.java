package lsea.entity;

import lsea.LaboratoryApplication;
import lsea.dto.CreateUserDto;
import lsea.dto.CreateUserGroupDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GlobalPermissions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

/**
 * Unit tests for the UserGroup class.
 */
@SpringBootTest(classes = { LaboratoryApplication.class })
@Transactional
public class UserGroupTest {

    /**
     * Test the creation of a new user group using the UserGroup create method.
     */
    @Test
    @Rollback
    public void testCreateUserGroup() throws GenericForbiddenError {
        // Arrange
        CreateUserGroupDto dto = Mockito.mock(CreateUserGroupDto.class);
        User creator = User.create(CreateUserDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build());
        creator.setGlobalPermission(GlobalPermissions.MODERATOR);

        // Act
        UserGroup userGroup = UserGroup.create(dto, creator);

        // Assert
        Assertions.assertNotNull(userGroup.getId());
        Assertions.assertEquals(dto.getName(), userGroup.getName());
        Assertions.assertEquals(dto.getDescription(), userGroup.getDescription());
        Assertions.assertEquals(GlobalPermissions.USER, userGroup.getGlobalPermission());
        Assertions.assertNotNull(userGroup.getCreatedAt());
    }

    /**
     * Test the creation of a new user group with an invalid global permission.
     */
    @Test
    @Rollback
    public void testCreateUserGroupThrowsGenericForbiddenError() {
        // Arrange
        CreateUserGroupDto dto = Mockito.mock(CreateUserGroupDto.class);
        User creator = User.create(CreateUserDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build());
        creator.setGlobalPermission(GlobalPermissions.USER);

        // Assert
        Assertions.assertThrows(GenericForbiddenError.class, () -> {
            // Act
            UserGroup.create(dto, creator);
        });
    }

    /**
     * Test successfully converting a user group to JSON.
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void testToJson() throws Exception {
        // Arrange
        User user = User.create(CreateUserDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .build());
        user.setGlobalPermission(GlobalPermissions.MODERATOR);
        UserGroup userGroup = UserGroup.create(CreateUserGroupDto.builder().build(), user);
        userGroup.setId(UUID.randomUUID());
        userGroup.setName("Test Group");
        userGroup.setDescription("Test group description");
        userGroup.setGlobalPermission(GlobalPermissions.USER);
        userGroup.setCreatedAt(new Date());

        // Act
        String json = userGroup.toJson();

        // Assert
        Assertions.assertNotNull(json);
    }
}
