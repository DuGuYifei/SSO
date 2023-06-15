package lsea.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import lsea.LaboratoryApplication;
import lsea.repository.UserRepository;
import lsea.utils.RandomBase64Generator;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

/* Requirement 2.1 */

/**
 * This class contains integration tests for the User Controller.
 * It uses TestRestTemplate to send HTTP requests to the test server and
 * verifies the response.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LaboratoryApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class UsersControllerTests {
        /**
         * User repository is used to check data in the database
         */
        @Autowired
        private UserRepository userRepository;

        /**
         * Port that is used by the test server.
         */
        @LocalServerPort
        private int port;

        /**
         * This is the MockMvc object that is used to send requests to the endpoints.
         */
        @Resource
        private MockMvc mockMvc;

        /**
         * Following test verifies whether the users endpoint is alive
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of ping endpoint")
        public void testPing() throws Exception {
                // Arrange
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/ping")
                                .contentType(MediaType.APPLICATION_JSON);

                // Act
                mockMvc.perform(requestBuilder)
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("Pong from user controller!"))
                                .andDo(MockMvcResultHandlers.print())
                                .andReturn();
        }

        /**
         * Following test verifies whether the user creation endpoint works as expected.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of create user endpoint - isOK")
        @Rollback
        public void testCreateUserIsOK() throws Exception {
                // Arrange
                String requestBody = "{ \"username\": \"testUser_first\", \"password\": \"password123\", \"email\": \"testuser1@example.com\" }";

                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                /* Requirement 9 */
                // isOK
                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andReturn();
        }

        /**
         * Following test verifies create user endpoint reject no content request.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of create user endpoint with no content")
        public void testCreateUserNoContent() throws Exception {
                // Arrange
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON);

                /* Requirement 9 */
                // no content
                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"))
                                .andDo(MockMvcResultHandlers.print())
                                .andReturn();
        }

        /**
         * Following test verifies whether the invalid e-mail address is rejected.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of create user endpoint with invalid email")
        public void testCreateUserWithInvalidEmail() throws Exception {
                String requestBody = "{ \"username\": \"testUser_second\", \"password\": \"password123\", \"email\": \"testuser2example.com\" }";

                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                /* Requirement 9 */
                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isForbidden())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(403))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(
                                                "A validation error occured: email must match \"^[a-z0-9_.+-]+@[a-z0-9-]+\\.[a-z0-9-.]+$\""))
                                .andDo(MockMvcResultHandlers.print())
                                .andReturn();
        }

        /**
         * Following test verifies whether the user creation endpoint rejects
         * registering already existing users.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of create user endpoint with already existing user")
        @Rollback
        public void testAlreadyExistingUser() throws Exception {
                // Arrange
                String requestBody = "{ \"username\": \"testUser_third\", \"password\": \"password123\", \"email\": \"testuser3@example.com\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                /* Requirement 9 */
                // isConflict
                // Act
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isConflict())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                                .value("User with email testuser3@example.com already exists."))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409));
        }

        /**
         * Following test verifies whether the user authorization endpoint works as
         * expected.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of authorize user endpoint")
        @Rollback
        public void testAuthorizeUser() throws Exception {
                // Arrange
                // Create a user `testUser_fourth`
                String password = RandomBase64Generator.generateShort();
                String email = "testuser4@example.com";
                String requestBody = "{ \"username\": \"testUser_fourth\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                // Authorize the user
                requestBody = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

                /* Requirement 9 */
                // authorized
                // Act
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.cookie().exists("token"));

        }

        /**
         * Following test verifies whether the user authorization endpoint rejects
         * wrong email or password.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of authorize user endpoint with wrong email or password")
        @Rollback
        public void testAuthorizeUserWithWrongEmailOrPassword() throws Exception {
                // Arrange
                // Create a user `testUser_fourth`
                String password = RandomBase64Generator.generateShort();
                String email = "testuser4@example.com";
                String requestBody = "{ \"username\": \"testUser_fifth\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                /* Requirement 9 */
                // cannot authorize
                // Act
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"email\": \"" + email + "\", \"password\": \"wrong_password\" }"))
                                // Assert
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                                .value("Invalid e-mail or password"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(403));
        }

        /**
         * Following test used to test whether admin user can successfully ban a user.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of authorize user endpoint with BanAndUnBanUser")
        @Rollback
        public void testBanUserAdminBan() throws Exception {
                // Arrange
                String password = RandomBase64Generator.generateShort();
                String email = "test_user@example.com";
                String requestBody = "{ \"username\": \"test_user\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                // Actual admin user tries to ban another user
                Cookie cookieSetAdmin = getAdminCookieHelper();

                requestBody = "{ \"emailAddress\": \"test_user@example.com\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/ban")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .cookie(cookieSetAdmin);

                /* Requirement 9 */
                // Actual admin ban the user
                // Act
                mockMvc.perform(requestBuilder)
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();
        }

        /**
         * Following test verifies whether the endpoint rejects the request if the
         * user is not admin.
         *
         * @throws Exception if the test fails
         */
        @Test
        public void testBanUserNotAdminBan() throws Exception {
                // Arrange
                String password = RandomBase64Generator.generateShort();
                String email = "test_user@example.com";
                String requestBody = "{ \"username\": \"test_user\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                password = RandomBase64Generator.generateShort();
                email = "test_user_not_admin@example.com";
                requestBody = "{ \"username\": \"test_user_not_admin\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String requestBodyNotAdmin = "{ \"email\": \"test_user_not_admin@example.com\", \"password\": \"" +
                                password + "\" }";

                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyNotAdmin);

                MvcResult result = mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                Cookie cookieSetNotAdmin = result.getResponse().getCookie("token");

                // Not admin user tries to ban another user
                requestBody = "{ \"emailAddress\": \"test_user@example.com\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/ban")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .cookie(cookieSetNotAdmin);

                /* Requirement 9 */
                // Not admin user tries to ban another user
                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isForbidden())
                                .andReturn();
        }

        /**
         * Following test used to successfully unban a user.
         *
         * @throws Exception if the test fails
         */
        @Test
        public void testUnBanAdminUnban() throws Exception {
                // Arrange
                String password = RandomBase64Generator.generateShort();
                String email = "test_user@example.com";
                String requestBody = "{ \"username\": \"test_user\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                // ban user
                Cookie cookieSetAdmin = getAdminCookieHelper();

                requestBody = "{ \"emailAddress\": \"test_user@example.com\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/ban")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .cookie(cookieSetAdmin);

                // Actual admin ban the user
                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                // Actual admin user tries to unban another user
                requestBody = "{ \"emailAddress\": \"test_user@example.com\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/unban")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .cookie(cookieSetAdmin);

                /* Requirement 9 */
                // Actual admin user tries to unban another user
                // Act
                mockMvc.perform(requestBuilder)
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();
        }

        /**
         * Following test verifies that a non-admin user cannot unban a user.
         *
         * @throws Exception if the test fails
         */
        @Test
        public void testUnBanNotAdminUnban() throws Exception {
                // Arrange
                String password = RandomBase64Generator.generateShort();
                String email = "test_user@example.com";
                String requestBody = "{ \"username\": \"test_user\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                password = RandomBase64Generator.generateShort();
                email = "test_user_not_admin@example.com";
                requestBody = "{ \"username\": \"test_user_not_admin\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody);

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String requestBodyNotAdmin = "{ \"email\": \"test_user_not_admin@example.com\", \"password\": \"" +
                                password + "\" }";

                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyNotAdmin);

                MvcResult result = mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                Cookie cookieSetNotAdmin = result.getResponse().getCookie("token");

                // Actual admin user tries to ban another user
                Cookie cookieSetAdmin = getAdminCookieHelper();

                requestBody = "{ \"emailAddress\": \"test_user@example.com\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/ban")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .cookie(cookieSetAdmin);

                // Actual admin ban the user
                mockMvc.perform(requestBuilder)
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                // Not admin user tries to unban another user
                requestBody = "{ \"emailAddress\": \"test_user@example.com\" }";
                requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/unban")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .cookie(cookieSetNotAdmin);

                /* Requirement 9 */
                // Not admin user tries to unban another user
                // Act
                mockMvc.perform(requestBuilder)
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isForbidden())
                                .andReturn();
        }

        /**
         * Following test update user endpoint.
         * Create a user and update it.
         *
         * @throws Exception if the test fails
         */
        /* Requirement 7.5 */
        @Test
        @DisplayName("Test of update user endpoint with UpdateUser")
        @Rollback
        public void testUpdateUserIsOK() throws Exception {
                // Arrange
                String password = RandomBase64Generator.generateShort();
                String email = "test@test.com";
                String requestBody = "{ \"username\": \"test\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                // Authorize the user
                requestBody = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                Cookie cookieSet = result.getResponse().getCookie("token");

                String requestBodyUpdate = "{ \"username\": \"testUpdate\", \"email\": \"test2@test.com\" }";

                /* Requirement 9 */
                // isOK update user
                // Act
                mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyUpdate)
                                .cookie(cookieSet))
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isOk());

                String username = userRepository.findByEmail("test2@test.com").get().getUsername();
                assertThat(username).isEqualTo("testUpdate");
        }

        /**
         * Following test update user endpoint.
         * Create a user and update it with invalid authorization token.
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of update user endpoint with UpdateUser with invalid authorization token")
        @Rollback
        public void testUpdateUserInValidToken() throws Exception {
                // Arrange
                String password = RandomBase64Generator.generateShort();
                String email = "test@test.com";
                String requestBody = "{ \"username\": \"test\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                // Authorize the user
                requestBody = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                String requestBodyUpdate = "{ \"username\": \"testUpdate\", \"email\": \"test2@test.com\" }";

                /* Requirement 9 */
                // isForbidden with invalid authorization token
                // Act
                mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyUpdate)
                                .cookie(new Cookie("token", "invalid")))
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isForbidden())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"));
        }

        /**
         * Following test update user endpoint.
         * Create a user and update it without content
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of update user endpoint with UpdateUser without content")
        @Rollback
        public void testUpdateUserNoContent() throws Exception {
                // Arrange
                String password = RandomBase64Generator.generateShort();
                String email = "test@test.com";
                String requestBody = "{ \"username\": \"test\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                // Authorize the user
                requestBody = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                Cookie cookieSet = result.getResponse().getCookie("token");

                /* Requirement 9 */
                // no content
                // Act
                mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .cookie(cookieSet))
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"));
        }

        /**
         * Following test update user endpoint.
         * Create a user and update it with no authorization to do so
         *
         * @throws Exception if the test fails
         */
        @Test
        @DisplayName("Test of update user endpoint with UpdateUser without content")
        @Rollback
        public void testUpdateUserNoCookie() throws Exception {
                // Arrange
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                String password = RandomBase64Generator.generateShort();
                String email = "test@test.com";
                String requestBody = "{ \"username\": \"test\", \"password\": \"" +
                                password +
                                "\", \"email\": \"" +
                                email +
                                "\" }";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                String requestBodyUpdate = "{ \"username\": \"testUpdate\", \"email\": \"test2@test.com\" }";

                /* Requirement 9 */
                // no cookie
                // Act
                mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyUpdate)
                                .headers(headers))
                                // Assert
                                .andExpect(MockMvcResultMatchers.status().isForbidden())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"));
        }

        /**
         * Used to return admin cookie
         *
         * @return Cookie admin cookie
         * @throws Exception if the performed request failed
         */
        private Cookie getAdminCookieHelper() throws Exception {
                String requestBodyAdmin = "{ \"email\": \"admin@example.com\", \"password\": \"adminadmin\" }";

                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyAdmin);

                MvcResult result = mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                Cookie cookieSetAdmin = result.getResponse().getCookie("token");
                return cookieSetAdmin;
        }
}
