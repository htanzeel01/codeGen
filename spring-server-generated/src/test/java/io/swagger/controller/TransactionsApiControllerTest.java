package io.swagger.controller;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.AccountImplamantation;
import io.swagger.service.TransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"CUSTOMER","EMPLOYEE"})
class TransactionsApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionImpl transactionImpl;
    private Transactions transactions;
    private UserToCreate userToCreate;
    private Account account;

    @BeforeEach
    public void setup(){
        userToCreate = new UserToCreate("Erjano","123456","erjano@gmail.com","Erjano","Baku", UserTypeEnum.ROLE_CUSTOMER);
        account = new Account("Mahedi",new BigDecimal(100), Account.AccountTypeEnum.CURRENT);
        account.setIban("NL55435435435435");
        account.setUser(userToCreate);

        transactions = new Transactions();
        transactions.setAccountfrom(account);
        transactions.setAccountto("NL96FGHTS123456V");
        transactions.setAmount(new BigDecimal(20.00));
        transactions.setTransactionDate(LocalDateTime.now());
        transactions.setUserperforming(Account.AccountTypeEnum.CURRENT);
    }

}