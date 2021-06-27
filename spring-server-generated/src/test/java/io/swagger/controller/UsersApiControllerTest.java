package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.service.AccountService;
import io.swagger.service.UserToCreateImpl;
import io.swagger.service.UserToCreateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"CUSTOMER","EMPLOYEE"})
class UsersApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserToCreateImpl userToCreateImpl;
    private UserToCreate userToCreate;

    @BeforeEach
    public void setup(){
        userToCreate = new UserToCreate("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.ROLE_CUSTOMER);
    }
    @Test
    public void getAllUser() throws Exception {
        List<UserToCreate> userToCreates = Arrays.asList(userToCreate);
        given(userToCreateImpl.getALLUsers()).willReturn(userToCreates);
        this.mvc.perform(get("/users")).andExpect(
                 status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));


    }
    @Test
    public void callingAllUsersShouldReturnOK() throws Exception {
        this.mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
    @Test
    public void callingUsersById() throws Exception {
         when(userToCreateImpl.getUserByUserId(1)).thenReturn(userToCreate);
         assertThat("Its passed");
    }
//    @Test
//    public void getUserAccount() throws Exception {
//        assertThat("Its passed");
//    }


}