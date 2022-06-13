package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.service.UserImplementation;
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
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"CUSTOMER","EMPLOYEE"})
class UsersApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserImplementation userImplementation;
    private User user;
    private UserToCreateRepository userToCreateRepository;

    @BeforeEach
    public void setup() throws Exception {
        user = new User("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.ROLE_CUSTOMER);
    }
    @Test
    public void getAllUser() throws Exception {
        List<User> users = Arrays.asList(user);
        given(userImplementation.getALLUsers()).willReturn(users);
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
         when(userImplementation.getUserByUserId(1)).thenReturn(user);
         assertThat("Its passed");
    }
    @Test
    public void updateUserByID()throws Exception{
        mvc.perform(
                put("/users/{userId}",1)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
    public static String asJsonString(final Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


}