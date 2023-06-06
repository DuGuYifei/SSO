package lsea.controllers;

import lsea.LaboratoryApplication;
import lsea.entity.User;
import lsea.repository.WebsiteRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.UUID;

/**
 * This class contains the tests for the WebsiteController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LaboratoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class WebsiteControllerTest {

    /**
     * This is the MockMvc object that is used to send requests to the endpoints.
     */
    @Resource
    private MockMvc mockMvc;

    /**
     * This is website repository that is used to access the database.
     */
    @Autowired
    private WebsiteRepository websiteRepository;

    /**
     * Test for successful creation of a website.
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController createOne")
    @Rollback
    public void testCreateOneIsOK() throws Exception {
        // Arrange
        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        // create a website
        String websiteRequest = "{\n" +
                "    \"displayName\": \"test\",\n" +
                "    \"redirectUrl\": \"test.com\"\n" +
                "}";

        /* Requirement 9 */
        // isOK
        // Act
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(websiteRequest))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    /**
     * Test for createOne method with no authorization to do so
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController createOne with no authorization to do so")
    @Rollback
    public void testCreateOneNoCookie() throws Exception {
        // Arrange
        String websiteRequest = "{\n" +
                "    \"displayName\": \"test\",\n" +
                "    \"redirectUrl\": \"test.com\"\n" +
                "}";

        /* Requirement 9 */
        // no cookies
        // Act
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .contentType("application/json;charset=UTF-8")
                        .content(websiteRequest))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                .andReturn();
    }

    /**
     * Test for createOne method with no content.
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController createOne with no content")
    @Rollback
    public void testCreateOneNoContent() throws Exception {
        // Arrange
        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        /* Requirement 9 */
        // no content
        // Act
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8"))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"))
                .andReturn();
    }

    /**
     * Test for createOne method with invalid authorization token.
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController createOne with invalid authorization token")
    @Rollback
    public void testCreateOneInvalidToken() throws Exception {
        // Arrange
        String websiteRequest = "{\n" +
                "    \"displayName\": \"test\",\n" +
                "    \"redirectUrl\": \"test.com\"\n" +
                "}";

        /* Requirement 9 */
        // invalid authorization token
        Cookie invalidCookie = new Cookie("token", "test");
        // Act
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .cookie(invalidCookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(websiteRequest))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                .andReturn();
    }

    /**
     * Test for deleteOne method.
     *
     * @throws Exception if the request is invalid
     */
    /* Requirement 7.6 */
    @Test
    @DisplayName("Test of WebsiteController deleteOne")
    @Rollback
    public void testDeleteOneIsOK() throws Exception {
        // Arrange
        // create a user and authorize it
        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        UUID userId = User.verifyToken(cookie.getValue());

        // create a website
        String websiteRequest = "{\n" +
                "    \"displayName\": \"test\",\n" +
                "    \"redirectUrl\": \"test.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(websiteRequest))
                .andReturn();

        // delete the website
        User user = new User();
        user.setId(userId);
        UUID websiteId = websiteRepository.findByUserAndDisplayName(user, "test").get(0).getId();

        String deleteRequest = "{\n" +
                "    \"websiteId\": \"" + websiteId.toString() + "\"\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/websites/delete")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8")
                .content(deleteRequest);

        /* Requirement 9 */
        // isOK
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    /**
     * Test for deleteOne method with no cookie.
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController deleteOne with no cookie")
    @Rollback
    public void testDeleteOneNoCookie() throws Exception{
        // Arrange
        // create a user and authorize it
        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        UUID userId = User.verifyToken(cookie.getValue());

        // create a website
        String websiteRequest = "{\n" +
                "    \"displayName\": \"test\",\n" +
                "    \"redirectUrl\": \"test.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(websiteRequest))
                .andReturn();

        // delete the website
        User user = new User();
        user.setId(userId);
        UUID websiteId = websiteRepository.findByUserAndDisplayName(user, "test").get(0).getId();

        String deleteRequest = "{\n" +
                "    \"websiteId\": \"" + websiteId.toString() + "\"\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/websites/delete")
                .contentType("application/json;charset=UTF-8")
                .content(deleteRequest);

        /* Requirement 9 */
        // no cookies
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                .andReturn();
    }

    /**
     * Test for deleteOne method with invalid authorization token.
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController deleteOne with invalid authorization token")
    @Rollback
    public void testDeleteOneInvalidToken() throws Exception{
        // Arrange
        // create a user and authorize it
        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        UUID userId = User.verifyToken(cookie.getValue());

        // create a website
        String websiteRequest = "{\n" +
                "    \"displayName\": \"test\",\n" +
                "    \"redirectUrl\": \"test.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(websiteRequest))
                .andReturn();

        // delete the website
        User user = new User();
        user.setId(userId);
        UUID websiteId = websiteRepository.findByUserAndDisplayName(user, "test").get(0).getId();

        String deleteRequest = "{\n" +
                "    \"websiteId\": \"" + websiteId.toString() + "\"\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/websites/delete")
                .cookie(new Cookie("token", "test"))
                .contentType("application/json;charset=UTF-8")
                .content(deleteRequest);

        /* Requirement 9 */
        // invalid authorization token
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                .andReturn();
    }

    /**
     * Test for deleteOne method with no website found.
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController deleteOne with no website found")
    @Rollback
    public void testDeleteOneNoWebsiteFound() throws Exception{
        // Arrange
        // create a user and authorize it
        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        String deleteRequestNoWebsiteId = "{\n" +
                "    \"websiteId\": \"" + UUID.randomUUID() + "\"\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/websites/delete")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8")
                .content(deleteRequestNoWebsiteId);

        /* Requirement 9 */
        // no website found
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Website not found for this user"))
                .andReturn();
    }

    /**
     * Test for deleteOne method by not owner.
     *
     * @throws Exception if the request is invalid
     */
    @Test
    @DisplayName("Test of WebsiteController deleteOne by not owner")
    @Rollback
    public void testDeleteOneNotOwner() throws Exception{
        // Arrange
        // create a user and authorize it
        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        UUID userId = User.verifyToken(cookie.getValue());

        // create a website
        String websiteRequest = "{\n" +
                "    \"displayName\": \"test\",\n" +
                "    \"redirectUrl\": \"test.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/websites")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(websiteRequest))
                .andReturn();

        // delete the website
        User user = new User();
        user.setId(userId);
        UUID websiteId = websiteRepository.findByUserAndDisplayName(user, "test").get(0).getId();

        String deleteRequest = "{\n" +
                "    \"websiteId\": \"" + websiteId.toString() + "\"\n" +
                "}";

        // create another user and authorize it
        userRequest = "{\n" +
                "    \"username\": \"new_test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@12.com\"\n" +
                "}";

        userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@12.com\"\n" +
                "}";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userRequest))
                .andReturn();

        MvcResult result2 = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        cookie = result2.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/websites/delete")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8")
                .content(deleteRequest);

        /* Requirement 9 */
        // not owner of this website
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User has no permission to delete this website"));
    }
}
