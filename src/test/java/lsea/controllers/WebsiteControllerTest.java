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
         * Test for deleteOne method.
         *
         * @throws Exception if the request is invalid
         */
        /* Requirement 7.6 */
        @Test
        @DisplayName("Test of WebsiteController")
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

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();
        }
}
