package ServiceTest;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserToCreateRepository;
import io.swagger.security.JwtTokenProvider;
import io.swagger.service.UserToCreateImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class)
class UserToCreateImplTest {

    @Mock
    private UserToCreateRepository userToCreateRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RegistrationDTO registrationDTO;
    private UserToCreate userToCreate;
    @InjectMocks
    private UserToCreateImpl userService;

    List<UserToCreate> userList = new ArrayList<>();
    // Make users to test with
    @BeforeEach
    public void setup() {
        registrationDTO = new RegistrationDTO("mah", "has!", "user@gmail.com", "mah", "has", UserTypeEnum.EMPLOYEE);
        UserToCreate user = new UserToCreate("mah", "has!", "user@gmail.com", "mah", "has", UserTypeEnum.EMPLOYEE);
        user.setUserId(2);
        userToCreate = user;
        userList.add(userToCreate);
    }
    @AfterEach
    public void tearDown() {
        userToCreate = null;
    }

    // Create an new user to the list of users test if it is created
    @Test
    public void createAnUser() throws Exception {
       UserToCreate userToCreate = userService.createUser(registrationDTO);
       assertEquals(userToCreateRepository.save(userToCreate), userToCreate);
    }

    @Test
    void cheackMail() {
    }

    @Test
    void login() {
    }

    @Test
    void getALLUsers() {
        when(userToCreateRepository.findAll()).thenReturn(userList);
        List<UserToCreate> userToCreates = userService.getALLUsers();
        assertEquals(userToCreates, userList);
    }

    @Test
    void getAllUsersByUserName() {
    }

    @Test
    void getUserByUserId() throws Exception {
        userToCreateRepository.save(userToCreate);
        when(userToCreateRepository.findUserToCreateByUserId(2)).thenReturn(userToCreate);
        UserToCreate user = userService.getUserByUserId(2);
        assertEquals(user, userToCreate);
    }

    @Test
    void updateUser() {
    }
}