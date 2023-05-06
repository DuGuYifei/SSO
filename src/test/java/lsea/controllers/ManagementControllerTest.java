package lsea.controllers;

import lsea.LaboratoryApplication;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

/**
 * This class is responsible for testing the ManagementController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LaboratoryApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ManagementControllerTest {

        /**
         * This is the MockMvc object that is used to send requests to the endpoints.
         */
        @Resource
        private MockMvc mockMvc;

        /**
         * This test method sends a Get request to the "/api/v1/management/analysis"
         * 
         * @throws Exception Exception of mockMvc.perform
         */
        @Test
        @DisplayName("Test of ManagementController")
        public void analysis() throws Exception {
                String userRequest = "{\n" +
                                "    \"username\": \"test\",\n" +
                                "    \"password\": \"12345678\",\n" +
                                "    \"email\": \"123@11.com\"\n" +
                                "}";

                String userAuthRequest = "{\n" +
                                "    \"password\": \"12345678\",\n" +
                                "    \"email\": \"123@11.com\"\n" +
                                "}";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType("application/json;charset=UTF-8")
                                .content(userRequest))
                                .andReturn();

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authorize")
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
                                        "    \"data\": \"" + logData + "\",\n" +
                                        "    \"logType\": 0\n" +
                                        "}";
                        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/logs")
                                        .cookie(cookie)
                                        .contentType("application/json;charset=UTF-8")
                                        .content(logRequest);

                        mockMvc.perform(requestBuilder)
                                        .andDo(MockMvcResultHandlers.print())
                                        .andReturn();
                }

                userAuthRequest = "{\n" +
                                "    \"password\": \"test_admin\",\n" +
                                "    \"email\": \"test_admin@example.com\"\n" +
                                "}";

                result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/authorize")
                                .contentType("application/json;charset=UTF-8")
                                .content(userAuthRequest))
                                .andReturn();

                cookie = result.getResponse().getCookie("token");

                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/management/analysis")
                                .param("numThreads", "5")
                                .cookie(cookie)
                                .contentType("application/json;charset=UTF-8");

                mockMvc.perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].data").value("test data55555"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data[4].data").value("test data999999999"))
                                .andDo(MockMvcResultHandlers.print());
        }
}
