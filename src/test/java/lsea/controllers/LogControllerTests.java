package lsea.controllers;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import lsea.LaboratoryApplication;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
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

/**
 * The LogControllerTests class contains unit tests for the LogController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LaboratoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class LogControllerTests {

    /**
     * This is the MockMvc object that is used to send requests to the endpoints.
     */
    @Resource
    private MockMvc mockMvc;

    /**
     * This test method sends a Post request to the "/api/v1/logs"
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of LogController createOne method")
    @Rollback
    public void testCreateOne() throws Exception {
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

        String logRequest = "{\n" + "    \"data\": \"test data\",\n" + "    \"logType\": 0\n" + "}";

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users")
                                .contentType("application/json;charset=UTF-8")
                                .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users/authorize")
                                .contentType("application/json;charset=UTF-8")
                                .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/logs")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8")
                .content(logRequest);

        /* Requirement 9 */
        // isOK
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }

    /**
     * This test method sends a Post request to the "/api/v1/logs" endpoint without a cookie.
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of LogController createOne method without cookie")
    @Rollback
    public void testCreateOneWithoutCookie() throws Exception {
        // Arrange
        String logRequest = "{\n" + "    \"data\": \"test data\",\n" + "    \"logType\": 0\n" + "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/logs")
                .contentType("application/json;charset=UTF-8")
                .content(logRequest);

        /* Requirement 9 */
        // no cookies
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * This test method sends a Post request to the "/api/v1/logs" endpoint without a content.
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of LogController createOne method without content")
    @Rollback
    public void testCreateOneWithoutContent() throws Exception {
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

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users")
                                .contentType("application/json;charset=UTF-8")
                                .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users/authorize")
                                .contentType("application/json;charset=UTF-8")
                                .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/logs")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // no content
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * This test method sends a Post request to the "/api/v1/logs" endpoint with an invalid authorization token.
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of LogController createOne method with invalid authorization token")
    @Rollback
    public void testCreateOneWithInvalidToken() throws Exception {
        // Arrange
        String logRequest = "{\n" + "    \"data\": \"test data\",\n" + "    \"logType\": 0\n" + "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/logs")
                .cookie(new Cookie("token", "123"))
                .contentType("application/json;charset=UTF-8")
                .content(logRequest);

        /* Requirement 9 */
        // invalid authorization token
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    /**
     * This test method sends a Get request to the "/api/v1/logs/generate-test-data
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of generateData method in LogController")
    @Rollback
    public void testGenerateDataIsOK() throws Exception {
        // Arrange
        int N = 1;

        String userRequest = "{\n" +
                "    \"username\": \"test\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        String userAuthRequest = "{\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"email\": \"123@11.com\"\n" +
                "}";

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users")
                                .contentType("application/json;charset=UTF-8")
                                .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users/authorize")
                                .contentType("application/json;charset=UTF-8")
                                .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/logs/generate-test-data")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8")
                .content(String.valueOf(N));

        /* Requirement 9 */
        // isOK
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * This test method sends a Get request to the "/api/v1/logs/generate-test-data" without content
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of generateData method in LogController without content")
    @Rollback
    public void testGenerateDataNoContent() throws Exception {
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

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users")
                                .contentType("application/json;charset=UTF-8")
                                .content(userRequest))
                .andReturn();

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users/authorize")
                                .contentType("application/json;charset=UTF-8")
                                .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/logs/generate-test-data")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // no content
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * This test method sends a Get request to the "/api/v1/logs/generate-test-data" without cookie
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of generateData method in LogController without cookie")
    @Rollback
    public void testGenerateDataNoCookie() throws Exception {
        // Arrange
        int N = 1;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/logs/generate-test-data")
                .contentType("application/json;charset=UTF-8")
                .content(String.valueOf(N));

        /* Requirement 9 */
        // no cookies
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * This test method sends a Get request to the "/api/v1/logs/generate-test-data" with invalid authorization token
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of generateData method in LogController with invalid authorization token")
    @Rollback
    public void testGenerateDataInvalidToken() throws Exception {
        // Arrange
        int N = 1;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/logs/generate-test-data")
                .cookie(new Cookie("token", "123"))
                .contentType("application/json;charset=UTF-8")
                .content(String.valueOf(N));

        /* Requirement 9 */
        // invalid authorization token
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
