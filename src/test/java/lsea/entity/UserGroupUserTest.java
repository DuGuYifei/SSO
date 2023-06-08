package lsea.entity;

import lsea.LaboratoryApplication;
import lsea.dto.AddUserToUserGroupDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GroupPermissions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.lang.reflect.Constructor;
import java.util.UUID;

/**
 * Unit tests for the UserGroupUser class.
 */
@SpringBootTest(classes = { LaboratoryApplication.class })
@Transactional
class UserGroupUserTest {

    /**
     * Create an AddUserToUserGroupDto instance.
     */
    private UserGroupUser groupUser;

    /**
     * Create an AddUserToUserGroupDto instance.
     */
    @BeforeEach
    void setUp() {
        groupUser = UserGroupUser.builder()
                .groupPermission(GroupPermissions.ADMIN)
                .build();
    }

    /**
     * Test the create method of UserGroupUser.
     *
     * @throws Exception if an error occurs
     */
    @Test
    @Rollback
    void testCreate() throws Exception {
        String userGroupId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        int role = GroupPermissions.REGULAR.ordinal();

        AddUserToUserGroupDto dto = createAddUserToUserGroupDto(userGroupId, userId, role);

        UserGroupUser userGroupUser = UserGroupUser.create(dto, groupUser);

        Assertions.assertNotNull(userGroupUser);
        Assertions.assertNotNull(userGroupUser.getId());
        Assertions.assertEquals(UUID.fromString(userGroupId), userGroupUser.getUserGroupId());
        Assertions.assertEquals(UUID.fromString(userId), userGroupUser.getUserId());
        Assertions.assertEquals(GroupPermissions.REGULAR, userGroupUser.getGroupPermission());
    }

    /**
     * Test the create method of UserGroupUser when the adder user has insufficient permissions.
     *
     * @throws Exception if an error occurs
     */
    @Test
    @Rollback
    void testCreateWithInsufficientPermissions() throws Exception {
        String userGroupId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        int role = GroupPermissions.ADMIN.ordinal();

        groupUser.setGroupPermission(GroupPermissions.MODERATOR);

        AddUserToUserGroupDto dto = createAddUserToUserGroupDto(userGroupId, userId, role);

        Assertions.assertThrows(GenericForbiddenError.class,
                () -> UserGroupUser.create(dto, groupUser));
    }

    /**
     * Test the create method of UserGroupUser when the adder user is a spectator.
     *
     * @throws Exception if an error occurs
     */
    @Test
    @Rollback
    void testCreateWithSpectatorPermissions() throws Exception {
        groupUser.setGroupPermission(GroupPermissions.SPECTATOR);

        String userGroupId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        int role = GroupPermissions.REGULAR.ordinal();

        AddUserToUserGroupDto dto = createAddUserToUserGroupDto(userGroupId, userId, role);

        Assertions.assertThrows(GenericForbiddenError.class,
                () -> UserGroupUser.create(dto, groupUser));
    }

    /**
     * Helper method to create an AddUserToUserGroupDto object for testing purposes using reflection.
     *
     * @param userGroupId the user group ID
     * @param userId      the user ID
     * @param role        the role/permission level
     * @return the created AddUserToUserGroupDto instance
     * @throws Exception if an error occurs
     */
    private AddUserToUserGroupDto createAddUserToUserGroupDto(String userGroupId, String userId, int role) throws Exception {
        Constructor<AddUserToUserGroupDto> constructor = AddUserToUserGroupDto.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        AddUserToUserGroupDto dto = constructor.newInstance();
        dto.setUserGroupId(userGroupId != null ? userGroupId : "");
        dto.setUserId(userId != null ? userId : "");
        dto.setRole(role);
        return dto;
    }
}
