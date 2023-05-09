package lsea.controllers;

import javax.annotation.Resource;
import lsea.LaboratoryApplication;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springfox.documentation.spring.web.json.Json;

/**
 * This is a JUnit test class for the IndexController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
  classes = { LaboratoryApplication.class },
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class IndexControllerTest {

  /**
   * This is the MockMvc object that is used to send requests to the endpoints.
   */
  @Resource
  private MockMvc mockMvc;

  /**
   * This test method sends a GET request to the "/" endpoint and checks if the
   * response is as expected.
   * It uses the Json class to create the expected response.
   *
   * @throws Exception if the test fails
   */
  @Test
  @DisplayName("Test of IndexController")
  public void sanityCheck() throws Exception {
    Json json = new Json(
      "{\"count\":0,\"data\":null,\"meta\":{\"message\":\"Hello World!\"}}"
    );
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .get("/")
          .contentType("application/json;charset=UTF-8")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().json(json.value()))
      .andDo(MockMvcResultHandlers.print())
      .andReturn();
  }
}
