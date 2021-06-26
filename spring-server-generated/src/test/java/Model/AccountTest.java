package Model;

import io.swagger.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;

    @BeforeEach
    public void setup(){
        account = new Account();
        account.setAccountType(Account.AccountTypeEnum.CURRENT);
        account.setBalance(BigDecimal.valueOf(500));
        account.setName("Mahedi");
        account.setIban("NL");

    }

    @Test
    public void AccountCanNotBeNull(){
        assertNotNull(account);
    }
    @Test
    public void ibanNotContainingNlWillThrowIllegalArgumentException() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            account.setIban("inho");
        });
    }
    @Test
    public void balancebelowzeroWillThrowIllegalArgumentException() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            account.setBalance(BigDecimal.valueOf(-500));
        });
    }
    @Test
    public void ibanNotContainingINHOWillThrowIllegalArgumentException(){
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            account.setIban("nl");
        });
    }
}