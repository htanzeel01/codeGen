package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.UserToCreateImpl;
import io.swagger.service.UserToCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
    private UserToCreateImpl userToCreateImpl;
    private RegistrationDTO registrationDTO;

    @BeforeEach
    public void setup(){
        registrationDTO = new RegistrationDTO("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.ROLE_CUSTOMER);
    }

    @Test
    @WithMockUser(username="admin",roles={"CUSTOMER","EMPLOYEE"})
    public void postingAUserToCreate201Created() throws Exception {
        String query="Customer";
        ObjectMapper mapper = new ObjectMapper();
        this.mvc
                .perform(
                        post("/register")
                                .param("userType",query)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(registrationDTO)))
                                 .andExpect(status().isCreated());
    }

}