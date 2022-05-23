package Model;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.Account;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class)
public class AccountTest {
    private Account account;

    @BeforeEach
    public void newUser(){
        //account = new Account("iban",new BigDecimal(100), Account.AccountTypeEnum.SAVINGS);
        account = new Account();
    }
    @Test
    public void AccountCanBeNull(){
        assertNull(account);
    }
    @Test
     public void ibanMusthavetherightformatshouldthrowexception(){
            Exception exception = assertThrows(NullPointerException.class,() -> account.setIban("vuidsb"));
            assertEquals("Iban must follow bank format", exception.getMessage());
     }

}
