package lsea.entity;

import lsea.LaboratoryApplication;
import lsea.dto.CreateUserGroupDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GlobalPermissions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

/**
 * Unit tests for the UserGroup class.
 */
@SpringBootTest(classes = { LaboratoryApplication.class })
public class UserGroupTest {

    /**
     * Test the creation of a new user group using the UserGroup create method.
     */
    @Test
    public void testCreateUserGroup() throws GenericForbiddenError {
        // Arrange
        CreateUserGroupDto dto = Mockito.mock(CreateUserGroupDto.class);
        User creator = new User();
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
    public void testCreateUserGroup_ThrowsGenericForbiddenError() {
        // Arrange
        CreateUserGroupDto dto = Mockito.mock(CreateUserGroupDto.class);
        User creator = new User();
        creator.setGlobalPermission(GlobalPermissions.USER);

        // Act & Assert
        Assertions.assertThrows(GenericForbiddenError.class, () -> {
            UserGroup.create(dto, creator);
        });
    }

    /**
     * Test the toJson method.
     */
    @Test
    public void testToJson() {
        // Arrange
        UserGroup userGroup = new UserGroup();
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
