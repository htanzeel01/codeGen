package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.UserToCreateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserToCreate.class)
@AutoConfigureMockMvc
class UsersApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean private UserToCreateImpl userToCreateImp;
    private RegistrationDTO registrationDTO;
    private UserToCreate userToCreate;

    @BeforeEach
    public void setup(){
        userToCreate = new UserToCreate("Mahedi","Mahedi123","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.CUSTOMER);
    }
    @Test
    public void getAllUserToCreateShouldReturnJsonArray() throws Exception {
        given(userToCreateImp.getAllUsersByUserName("Mahedi")).willReturn((userToCreate));
        this.mvc.perform(get("/users")).andExpect(
                status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    @Test
    public void postingAUserShouldReturn201Created() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        registrationDTO = new RegistrationDTO("Mahedi","Mahedi123","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.CUSTOMER);
                 this.mvc
                .perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated());
    }
}