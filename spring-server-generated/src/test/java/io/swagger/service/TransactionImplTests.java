package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.Account;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Swagger2SpringBoot.class)
public class TransactionImplTests {
    @Mock
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Mock
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    private RegistrationDTO registrationDTO;

    private User firstUser;
    private User secondUser;
    private Account firstAccount;
    private Account secondAccount;
    @Autowired
    private UserImplementation userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    @Mock
    private TransactionRepository transactionRepository;

    List<User> userList = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();

    private String firstIban = "NL55INHO0171";
    private String secondIban = "NL55INHO3248";
    private Transaction transaction;

    @BeforeEach
    public void setup() throws Exception {
        registrationDTO = new RegistrationDTO("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE);
        RegistrationDTO r1 = new RegistrationDTO("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER);
        firstUser = new User("mah", "mah!", "mah@.nl", "mah", "mri", UserTypeEnum.ROLE_EMPLOYEE);
        secondUser =  new User("rick","rick!","rick@.nl","rick","ricki",UserTypeEnum.ROLE_CUSTOMER);
        firstUser.setUserId(1);
        secondUser.setUserId(2);
        userList.add(firstUser);
        userList.add(secondUser);
        userRepository.saveAll(userList);
        firstAccount = new Account(new BigDecimal(1000), Account.AccountTypeEnum.CURRENT);
        firstAccount.setUser(firstUser);
        firstAccount.setIban(firstIban);
        firstAccount.setBalance(new BigDecimal(10));
        secondAccount = new Account(new BigDecimal(1000), Account.AccountTypeEnum.CURRENT);
        secondAccount.setUser(secondUser);
        secondAccount.setIban(secondIban);
        accounts.add(firstAccount);
        accounts.add(secondAccount);
        transaction = new Transaction();
        transaction.setAccountfrom(firstAccount);
    }
    @AfterEach
    public void tearDown() {
        firstUser = null;
        firstAccount = null;
        secondUser = null;
        secondAccount = null;
    }
    @Test
    public void createTransaction() throws Exception {
        accountRepository.save(firstAccount);
        accountRepository.save(secondAccount);
        transactionRepository.save(transaction);
        when(transactionRepository.getAllByAccountto(firstAccount.getIban())).thenReturn((Iterable<Transaction>) transaction);
        Transaction transactions = transactionService.getTransactionsById(1);
        assertEquals(transactions, this.transaction);
    }

}
