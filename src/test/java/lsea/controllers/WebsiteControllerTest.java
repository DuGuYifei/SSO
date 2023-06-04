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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.UUID;

/**
 * This class contains the tests for the WebsiteController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LaboratoryApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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
         * Test for createOne method.
         *
         * @throws Exception if the request is invalid
         */
        @Test
        @DisplayName("Test of WebsiteController createOne")
        public void createOne() throws Exception {
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

                /* Requirement 9 */
                // isOK
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/websites")
                                .cookie(cookie)
                                .contentType("application/json;charset=UTF-8")
                                .content(websiteRequest))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

                /* Requirement 9 */
                // no cookies
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/websites")
                                .contentType("application/json;charset=UTF-8")
                                .content(websiteRequest))
                        .andExpect(MockMvcResultMatchers.status().isForbidden())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                        .andReturn();

                /* Requirement 9 */
                // invalid token
                Cookie invalidCookie = new Cookie("token", "test");
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/websites")
                                .cookie(invalidCookie)
                                .contentType("application/json;charset=UTF-8")
                                .content(websiteRequest))
                        .andExpect(MockMvcResultMatchers.status().isForbidden())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                        .andReturn();

                /* Requirement 9 */
                // no content
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/websites")
                                .cookie(cookie)
                                .contentType("application/json;charset=UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Something went wrong"))
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
        public void deleteOne() throws Exception {
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
                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                requestBuilder = MockMvcRequestBuilders
                        .post("/api/v1/websites/delete")
                        .contentType("application/json;charset=UTF-8")
                        .content(deleteRequest);

                /* Requirement 9 */
                // no cookies
                mockMvc.perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isForbidden())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No cookies found"))
                        .andReturn();

                requestBuilder = MockMvcRequestBuilders
                        .post("/api/v1/websites/delete")
                        .cookie(new Cookie("token", "test"))
                        .contentType("application/json;charset=UTF-8")
                        .content(deleteRequest);

                /* Requirement 9 */
                // invalid token
                mockMvc.perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isForbidden())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid token"))
                        .andReturn();

                String deleteRequestNoWebsiteId = "{\n" +
                        "    \"websiteId\": \"" + UUID.randomUUID() + "\"\n" +
                        "}";

                requestBuilder = MockMvcRequestBuilders
                        .post("/api/v1/websites/delete")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(deleteRequestNoWebsiteId);

                /* Requirement 9 */
                // no website found
                mockMvc.perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Website not found for this user"))
                        .andReturn();

                userAuthRequest = "{\n" +
                        "    \"password\": \"test_admin\",\n" +
                        "    \"email\": \"test_admin@example.com\"\n" +
                        "}";

                result = mockMvc
                        .perform(
                                MockMvcRequestBuilders
                                        .post("/api/v1/users/authorize")
                                        .contentType("application/json;charset=UTF-8")
                                        .content(userAuthRequest))
                        .andReturn();

                cookie = result.getResponse().getCookie("token");

                requestBuilder = MockMvcRequestBuilders
                        .post("/api/v1/websites/delete")
                        .cookie(cookie)
                        .contentType("application/json;charset=UTF-8")
                        .content(deleteRequest);

                /* Requirement 9 */
                // not owner of this website
                mockMvc.perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Website not found for this user"))
                        .andReturn();
        }
}
