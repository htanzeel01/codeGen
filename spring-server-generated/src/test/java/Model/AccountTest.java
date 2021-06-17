package Model;

import io.swagger.model.Account;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class AccountTest {
    Account account;

    @BeforeEach
    public void newAccount(){

        account = new Account();
        account.setIban("NLxxINHO0xxxxxxxxx");
        account.setAccountType(Account.AccountTypeEnum.CURRENT);
        account.setBalance(BigDecimal.valueOf(500));
        account.setName("Mahedi");
        UserToCreate user = new UserToCreate();
        user.setEmail("mahedi@gmail.com");
        user.setFirstName("Mahedi");
        user.setLastName("Mridul");
        user.setUserType(UserTypeEnum.CUSTOMER);
        user.setUsername("Mahedi");
        user.setPassword("Mahedi1243");
        account.setUser(user);
       // account = new Account("NLxxxINHOxxxxxx",BigDecimal.valueOf(100), Account.AccountTypeEnum.SAVINGS);
    }
    @Test
    public void AccountIbanCanNotBeNull(){
        assertNotNull(account);
    }
@Test()
 public void ibanNotContainingNlWillThrowIllegalArgumentException(){
    Assertions.assertThrows(ResponseStatusException.class, () -> {
        account.setIban("sore");
    });

 }

 @Test()
    public void balancebelowzeroWillThrowIllegalArgumentException(){
     Assertions.assertThrows(ResponseStatusException.class, () -> {
         account.setBalance(BigDecimal.valueOf(-500));
     });

 }

}
