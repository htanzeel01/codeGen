package Model;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class)
class UserToCreateTest {
    private UserToCreate user ;

    @BeforeEach
    public void setup(){
        user = new UserToCreate();
    }
    @Test
    public void UserToCreateCanNotBeNull(){
        assertNotNull(user);
    }


    @Test
    public void getUserId() {
    }
}