package lsea.entity;

import lsea.LaboratoryApplication;
import lsea.dto.AddUserToUserGroupDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GroupPermissions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

/**
 * Unit tests for the UserGroupUser class.
 */
@SpringBootTest(classes = { LaboratoryApplication.class })
class UserGroupUserTest {

    /**
     * Create an AddUserToUserGroupDto instance.
     */
    private UserGroupUser adderUser;

    /**
     * Create an AddUserToUserGroupDto instance.
     */
    @BeforeEach
    void setUp() {
        adderUser = UserGroupUser.builder()
                .groupPermission(GroupPermissions.ADMIN)
                .build();
    }

    /**
     * Test the create method of UserGroupUser.
     */
    @Test
    void testCreate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, GenericForbiddenError {
        String userGroupId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        int role = GroupPermissions.REGULAR.ordinal();

        AddUserToUserGroupDto dto = createAddUserToUserGroupDto(userGroupId, userId, role);

        UserGroupUser userGroupUser = UserGroupUser.create(dto, adderUser);

        Assertions.assertNotNull(userGroupUser);
        Assertions.assertNotNull(userGroupUser.getId());
        Assertions.assertEquals(UUID.fromString(userGroupId), userGroupUser.getUserGroupId());
        Assertions.assertEquals(UUID.fromString(userId), userGroupUser.getUserId());
        Assertions.assertEquals(GroupPermissions.REGULAR, userGroupUser.getGroupPermission());
    }

    /**
     * Test the create method of UserGroupUser when the adder user has insufficient permissions.
     */
    @Test
    void testCreateWithInsufficientPermissions() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String userGroupId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        int role = GroupPermissions.ADMIN.ordinal();

        adderUser.setGroupPermission(GroupPermissions.MODERATOR);

        AddUserToUserGroupDto dto = createAddUserToUserGroupDto(userGroupId, userId, role);

        Assertions.assertThrows(GenericForbiddenError.class,
                () -> UserGroupUser.create(dto, adderUser));
    }

    /**
     * Test the create method of UserGroupUser when the adder user is a spectator.
     */
    @Test
    void testCreateWithSpectatorPermissions() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        adderUser.setGroupPermission(GroupPermissions.SPECTATOR);

        String userGroupId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        int role = GroupPermissions.REGULAR.ordinal();

        AddUserToUserGroupDto dto = createAddUserToUserGroupDto(userGroupId, userId, role);

        Assertions.assertThrows(GenericForbiddenError.class,
                () -> UserGroupUser.create(dto, adderUser));
    }

    /**
     * Helper method to create an AddUserToUserGroupDto object for testing purposes using reflection.
     *
     * @param userGroupId the user group ID
     * @param userId      the user ID
     * @param role        the role/permission level
     * @return the created AddUserToUserGroupDto instance
     * @throws NoSuchMethodException     if the constructor is not found
     * @throws IllegalAccessException    if the constructor cannot be accessed
     * @throws InvocationTargetException if the constructor invocation fails
     * @throws InstantiationException    if the object cannot be instantiated
     */
    private AddUserToUserGroupDto createAddUserToUserGroupDto(String userGroupId, String userId, int role) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<AddUserToUserGroupDto> constructor = AddUserToUserGroupDto.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        AddUserToUserGroupDto dto = constructor.newInstance();
        dto.setUserGroupId(userGroupId != null ? userGroupId : "");
        dto.setUserId(userId != null ? userId : "");
        dto.setRole(role);
        return dto;
    }
}
