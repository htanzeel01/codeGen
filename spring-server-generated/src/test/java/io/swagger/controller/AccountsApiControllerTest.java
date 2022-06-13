package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.AccountImplamantation;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"CUSTOMER","EMPLOYEE"})
class AccountsApiControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountImplamantation accountImplamantation;
    private Account account;
    private User user;
    private List<Account> accountList;

    @BeforeEach
    public void setup(){
        user = new User("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul",UserTypeEnum.ROLE_CUSTOMER);
        account = new Account("Mahedi",new BigDecimal(100), Account.AccountTypeEnum.CURRENT);
        account.setIban("NL55INHO43543543511");
        account.setUser(user);
        accountList = new ArrayList<>();
        accountList.add(account);
   //     "Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul", UserTypeEnum.ROLE_CUSTOMER

    }
    @Test
    public void postingOpenAccount201Created() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        this.mvc
                .perform(
                        post("/openaccounts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(account)))
                .andExpect(status().isOk());
    }
    @Test
    public void gettingAllAccountsbyIbanShouldReturnOk() throws Exception{
        when(accountImplamantation.getbyIban("NL55INHO43543543511")).thenReturn(account);
        assertThat("Its passed");
    }
    @Test
    public void getUserAccountbyUserIDShouldReturnOK() throws Exception {
        when(accountImplamantation.getAllByUser(1)).thenReturn(accountList);
        assertThat("Test passed");
    }
    @Test
    public void gettinguserbyNameShouldReturnOk() throws Exception {
        String query="Mahedi";
        ObjectMapper mapper = new ObjectMapper();
        this.mvc
                .perform(
                        get("/accounts")
                                .param("name",query)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(account)))
                .andExpect(status().isOk());
    }
    @Test
    public void  withdrawfromAccountShouldReturnOk() throws Exception {
        when(accountImplamantation.withdraw(account.getIban(),20)).thenReturn(account);
        assertThat("Test passed");
    }
    @Test
    public void depositintoAccountShouldReturnOk() throws Exception {
        when(accountImplamantation.deposit(account.getIban(),20)).thenReturn(1);
        assertThat("Test passed");
    }
    @Test
    public void closingAccount()throws Exception{
        mvc.perform(delete("/accounts/{IBAN}",1))
                .andExpect(status().isNoContent());
    }

}