package Model;

import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDTOTest {
    private RegistrationDTO registrationDTO;

    @BeforeEach
    public void setup(){
        registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("mahedi@gmail.com");
        registrationDTO.setFirstName("Mahedi");
        registrationDTO.setLastName("Mridul");
        registrationDTO.setUsertype(UserTypeEnum.CUSTOMER);

        registrationDTO.setUserName("Mahedi");
        registrationDTO.setPassword("Mahedi1243");
    }
    @Test
    public void UserToCreateCanNotBeNull(){
        assertNotNull(registrationDTO);
    }

    @Test
    public void emaildoesNotConsistDotWillThrowIllegalArgumentException () {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
        registrationDTO.setEmail("hello@nl");
        });
    }
    @Test
    public void emailNotContainingAtWillThrowIllegalArgumentException () {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            registrationDTO.setEmail("hello.nl");
        });
    }
}