package lsea.controllers;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import lsea.LaboratoryApplication;
import org.junit.Assert;
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
 * This class is responsible for testing the ManagementController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LaboratoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ManagementControllerTest {

    /**
     * This is the MockMvc object that is used to send requests to the endpoints.
     */
    @Resource
    private MockMvc mockMvc;

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-longest-five"
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test of ManagementController")
    @Rollback
    public void testAnalysisLongestIsOK() throws Exception {
        // Arrange
        generateTestData();

        String userAuthRequest = "{\n" +
                "    \"password\": \"test_admin\",\n" +
                "    \"email\": \"test_admin@example.com\"\n" +
                "}";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/analysis-longest-five")
                .param("numThreads", "5")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // isOK
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data[0].data")
                        .value("test data55555"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data[4].data")
                        .value("test data999999999"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-longest-five"
     * with no authorization to do so.
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test of ManagementController - with no authorization to do so")
    @Rollback
    public void testAnalysisLongestNoCookies() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/analysis-longest-five")
                .param("numThreads", "5")
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // no cookies
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-longest-five"
     * with no numThreads.
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test of ManagementController - noContent")
    @Rollback
    public void testAnalysisLongestNoContent() throws Exception {
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
                .get("/api/v1/management/analysis-longest-five")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // no content
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-longest-five"
     * with invalid authorization token.
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test of ManagementController - invalidToken")
    @Rollback
    public void testAnalysisLongestInvalidToken() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/analysis-longest-five")
                .param("numThreads", "5")
                .cookie(new Cookie("token", "test"))
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // invalid authorization token
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-shortest-five"
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test of ManagementController - isOK")
    @Rollback
    public void testAnalysisShortestIsOK() throws Exception {
        // Arrange
        generateTestData();

        String userAuthRequest = "{\n" +
                "    \"password\": \"test_admin\",\n" +
                "    \"email\": \"test_admin@example.com\"\n" +
                "}";

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users/authorize")
                                .contentType("application/json;charset=UTF-8")
                                .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/analysis-shortest-five")
                .param("numThreads", "5")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // isOK
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data[0].data")
                                .value("test data4444"))
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.data[4].data")
                                .value("test data"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-shortest-five"
     * with no authorization to do so.
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test analysis-longest-five with no authorization to do so")
    public void testAnalysisShortestNoCookie() throws Exception {
        // Arrange
        // Send request to analysis-longest-five endpoint without a cookie
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/analysis-longest-five")
                .param("numThreads", "5")
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // no cookies
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-shortest-five"
     * with NoContent.
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test analysis-longest-five with valid cookie but no content")
    public void testAnalysisShortestNoContent() throws Exception {
        // Arrange
        // Authenticate as admin user
        String userAuthRequest = "{\n" +
                "    \"password\": \"test_admin\",\n" +
                "    \"email\": \"test_admin@example.com\"\n" +
                "}";
        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/users/authorize")
                                .contentType("application/json;charset=UTF-8")
                                .content(userAuthRequest))
                .andReturn();
        Cookie cookie = result.getResponse().getCookie("token");

        // Send request to analysis-longest-five endpoint with valid cookie but no content
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/analysis-longest-five")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // no content
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method sends a Get request to the "/api/v1/management/analysis-shortest-five"
     * with invalid authorization token.
     *
     * @throws Exception Exception of mockMvc.perform
     */
    @Test
    @DisplayName("Test analysis-longest-five with invalid authorization token")
    public void testAnalysisShortestInvalidToken() throws Exception {
        // Arrange
        // Send request to analysis-longest-five endpoint with invalid authorization token
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/analysis-longest-five")
                .param("numThreads", "5")
                .cookie(new Cookie("token", "test"))
                .contentType("application/json;charset=UTF-8");

        /* Requirement 9 */
        // invalid authorization token
        // Act
        mockMvc.perform(requestBuilder)
                // Assert
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * This test method test successfully send a post request to the "/api/v1/management/report"
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of ManagementController report - isOK")
    @Rollback
    public void testGenerateReportIsOK() throws Exception {
        // Arrange
        generateTestData();

        String userAuthRequest = "{\n" +
                "    \"password\": \"test_admin\",\n" +
                "    \"email\": \"test_admin@example.com\"\n" +
                "}";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        String request = "{\n" +
                "    \"numThreads\": 5,\n" +
                "    \"iterations\": 1\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/management/report")
                .cookie(cookie)
                .contentType("application/json;charset=UTF-8")
                .content(request);

        // Act
        result = mockMvc.perform(requestBuilder)
                .andReturn();

        int status = result.getResponse().getStatus();
        byte[] content = result.getResponse().getContentAsByteArray();

        // Assert
        Assert.assertEquals(status, 200);
        // xlsx file
        Assert.assertEquals(content[0], (byte) 0x50);
        Assert.assertEquals(content[1], (byte) 0x4B);
        Assert.assertEquals(content[2], (byte) 0x03);
        Assert.assertEquals(content[3], (byte) 0x04);
    }

    @Test
    @DisplayName("Test of ManagementController report - no token in cookie")
    @Rollback
    public void testGenerateReportNoToken() throws Exception {
        // Arrange
        String request = "{\n" +
                "    \"numThreads\": 5,\n" +
                "    \"iterations\": 1\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/management/report")
                .cookie(new Cookie("notoken", "test"))
                .contentType("application/json;charset=UTF-8")
                .content(request);

        // Act
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("A validation error occured: No token found in cookies"));
    }

    /**
     * This test method test successfully send a get request to the "/api/v1/management/perform-test-on-database"
     *
     * @throws Exception if the test fails
     */
    @Test
    @DisplayName("Test of ManagementController database test report")
    @Rollback
    public void testDatabaseReport() throws Exception {
        // Arrange
        String userAuthRequest = "{\n" +
                "    \"password\": \"test_admin\",\n" +
                "    \"email\": \"test_admin@example.com\"\n" +
                "}";
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/users/authorize")
                        .contentType("application/json;charset=UTF-8")
                        .content(userAuthRequest))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("token");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/management/perform-test-on-database")
                .cookie(cookie)
                .param("logsNumber", "1");

        // Act
        result = mockMvc.perform(requestBuilder)
                .andReturn();

        // Assert
        Assert.assertEquals(result.getResponse().getStatus(), 200);
        Assert.assertTrue(result.getResponse().getContentAsString().contains("totalDurationWrite"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("totalDurationRead"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("averageDurationWrite"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("averageDurationRead"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("medianDurationWrite"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("medianDurationRead"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("standardDeviationWrite"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("standardDeviationRead"));
        Assert.assertTrue(result.getResponse().getContentAsString().contains("writeMemoryUsage"));
    }

    /**
     * Generate data used for testing
     *
     * @throws Exception Exception of mockMvc.perform
     */
    private void generateTestData() throws Exception {
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

        for (int i = 0; i < 10; i++) {
            StringBuilder logData = new StringBuilder("test data");
            for (int j = 0; j < i; j++) {
                logData.append(i);
            }
            String logRequest = "{\n" +
                    "    \"data\": \"" +
                    logData +
                    "\",\n" +
                    "    \"logType\": 0\n" +
                    "}";
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post("/api/v1/logs")
                    .cookie(cookie)
                    .contentType("application/json;charset=UTF-8")
                    .content(logRequest);

            mockMvc.perform(requestBuilder)
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        }
    }
}
