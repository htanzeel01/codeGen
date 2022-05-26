package Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.security.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoginApiControllerTest {

    private static final String iban1 = "TEST02374";
    private static final String testIban2 = "TEST024545";
    private static final String firstName = "mah";
    private static final String lastName = "mri";
    private static final String emailAddress = "mri@gmail.nl";
    private static final String password = "mridul01";
    private static final UserTypeEnum role = UserTypeEnum.CUSTOMER;

    private ArrayList<UserTypeEnum> roles = new ArrayList<>();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RegistrationDTO registrationDTO;
    private UserToCreate userToCreate;


    @Autowired
    private ObjectMapper objectMapper;

    /*private User testUser;
    private Account testAccount;

    private String jwtToken;

    public User createMockUser() {

        roles.add(Role.EMPLOYEE);
        User u = new User("testuser","testln","12345","testuser@example.com",encoder.encode("secret"), roles);

        return userService.addUser(u);
    }

    public User createTestUser() {
        User u = new User(firstName,lastName,phoneNumber,emailAddress,encoder.encode(password), roles);
        return userService.addUser(u);

    }



    public Account createMockAccount(User user, String iban, BigDecimal balance, AccountType accountType) {
        Account account = new Account(iban, user, balance, accountType);
        accountService.addAccount(account);
        return account;
    }
*/
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getlogin() {
    }
}