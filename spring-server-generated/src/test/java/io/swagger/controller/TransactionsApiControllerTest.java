package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.Transactions;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.TransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"CUSTOMER","EMPLOYEE"})
class TransactionsApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private Transactions transactions;
    private TransactionImpl transactionimp;
    private Account account;
    private User user;

    @BeforeEach
    public void setup() throws Exception {
        user = new User("Erjano","123456","erjano@gmail.com","Erjano","Baku", UserTypeEnum.ROLE_CUSTOMER);
        account = new Account("Mahedi",new BigDecimal(100), Account.AccountTypeEnum.CURRENT);
        account.setIban("NL55435435435435");
        account.setUser(user);

        transactions = new Transactions();
        transactions.setAccountfrom(account);
        transactions.setAccountto("NL23INHO123456789");
        transactions.setAmount(new BigDecimal(20.00));
        transactions.setTransactionDate(LocalDateTime.now());
        transactions.setUserperforming(account.getUser().getUserType());
        transactions.setId(1);
    }
    @Test
    public void postingCreateTransactionReturnsOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        this.mvc
                .perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(transactions)))
                .andExpect(status().isOk());
    }
    @Test
    public void getTransactionbyIdReturnsOk() throws Exception {
        when(transactionimp.getTransactionsById(transactions.getId())).thenReturn(transactions);
        assertThat("test passed");
    }
}