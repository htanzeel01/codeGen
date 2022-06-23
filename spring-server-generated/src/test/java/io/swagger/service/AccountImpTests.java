package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.Account;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Swagger2SpringBoot.class)
public class AccountImpTests {
    @Mock
    private AccountRepository accountRepository;

    @Autowired
    @InjectMocks
    private AccountImplamantation accountService;
    private Account account;
    private RegistrationDTO registrationDTO;
    List<Account> accountList = new ArrayList<>();
    private ArrayList<UserTypeEnum> roles = new ArrayList<>();
    private User testUser;
    private Account testAccount;
    private String testIban1 = "TESTIBAN001";
    private String testIban2 = "TESTIBAN002";

    @BeforeEach
    public void setup() throws Exception {
        roles.add(UserTypeEnum.ROLE_EMPLOYEE);
        registrationDTO = new RegistrationDTO("mahedi", "has!", "user@gmail.com", "mah", "has", UserTypeEnum.ROLE_EMPLOYEE);
        User user = new User("mahedi", "has!", "user@gmail.com", "mah", "has", UserTypeEnum.ROLE_EMPLOYEE);
        user.setUserId(4);
        testUser = user;
        testAccount = new Account(BigDecimal.valueOf(95.53), Account.AccountTypeEnum.CURRENT);
        testAccount.setUser(user);
        testAccount.setIban("NL55INHO");
        accountList.add(testAccount);
    }

    @AfterEach
    public void tearDown() {
        testUser = null;
        testAccount = null;
    }

    @Test
    public void addAccount() throws Exception {
        accountRepository.save(testAccount);
        when(accountRepository.getAllByUserid(testUser.getUserId())).thenReturn(accountList);
        List<Account> accountList1 = accountRepository.getAllByUserid(testUser.getUserId());
        assertEquals(accountList1, accountList);
    }

    @Test
    public void getAccounts() throws Exception {
        accountRepository.save(testAccount);
        when(accountRepository.getAllByUserid(testUser.getUserId())).thenReturn(accountList);
        List<Account> accountList1 = accountRepository.getAllByUserid(testUser.getUserId());
        assertEquals(accountList1, accountList);
    }

    @Test
    public void getAccountByIban(){
        accountRepository.save(testAccount);
        when(accountRepository.getAccountByIban(testAccount.getIban())).thenReturn(testAccount);
        Account account1 =  accountRepository.getAccountByIban(testAccount.getIban());
        assertEquals(account1, testAccount);
    }
}
