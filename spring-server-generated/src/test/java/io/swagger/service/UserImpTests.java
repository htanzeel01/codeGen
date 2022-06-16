package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = Swagger2SpringBoot.class)
public class UserImpTests {
    @Mock
    private UserToCreateRepository userToCreateRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RegistrationDTO registrationDTO;
    private User user;
    @InjectMocks
    private UserImplementation userService;

    List<User> userList = new ArrayList<>();
    // Make users to test with
    @BeforeEach
    public void setup() {
        registrationDTO = new RegistrationDTO("mah", "has!", "user@gmail.com", "mah", "has", UserTypeEnum.ROLE_EMPLOYEE);
        User user = new User("mah", "has!", "user@gmail.com", "mah", "has", UserTypeEnum.ROLE_EMPLOYEE);
        user.setUserId(2);
        this.user = user;
        userList.add(this.user);
    }
    @AfterEach
    public void tearDown() {
        user = null;
    }

    // Create an new user to the list of users test if it is created
    @Test
    public void createAnUser() throws Exception {
        User user = userService.createUser(registrationDTO);
        assertEquals(userToCreateRepository.save(user), user);
    }

    @Test
    void getALLUsers() {
        when(userToCreateRepository.findAll()).thenReturn(userList);
        List<User> users = userService.getALLUsers();
        assertEquals(users, userList);
    }

    @Test
    void getUsersByUserName() {
        userToCreateRepository.save(user);
        when(userToCreateRepository.findUserByUsername("mah")).thenReturn(user);
        User user = userService.getAllUsersByUserName("mah");
        assertEquals(user, this.user);
    }

    @Test
    void getUserByUserId() throws Exception {
        userToCreateRepository.save(user);
        when(userToCreateRepository.findUserByUserId(2)).thenReturn(user);
        User user = userService.getUserByUserId(2);
        assertEquals(user, this.user);
    }
}
