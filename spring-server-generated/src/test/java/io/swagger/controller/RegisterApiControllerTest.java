package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.UserToCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RegisterApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserToCreateService userToCreateService;
    private UserToCreate userToCreate;

//    @BeforeEach
//    public void setup(){
//        userToCreate = new UserToCreate("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.CUSTOMER);
//    }

    @Test
    public void postingAUserToCreate201Created() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserToCreate userToCreate= new UserToCreate("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul", UserTypeEnum.CUSTOMER);
        this.mvc
                .perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(userToCreate)))
                .andExpect(status().isCreated());
    }

}