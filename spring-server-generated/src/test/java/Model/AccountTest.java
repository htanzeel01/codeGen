package Model;

import io.swagger.model.Account;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

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
