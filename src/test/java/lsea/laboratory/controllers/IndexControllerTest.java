package lsea.laboratory.controllers;

import lsea.LaboratoryApplication;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LaboratoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test of IndexController")
    public void sanityCheck() throws Exception {
        Json json = new Json("{\"count\":0,\"data\":null,\"meta\":{\"message\":\"Hello World!\"}}");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json.value()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
