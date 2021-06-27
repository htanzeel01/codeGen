package io.swagger.controller;

import io.swagger.model.Account;
import io.swagger.model.Transactions;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionApiControllerTest {
    private Transactions transactions;
    private Account account;
    private UserToCreate userToCreate;



    @BeforeEach
    public void setup(){
        userToCreate = new UserToCreate("Mahedi","Mahedi1243","mahedi@gmail.com","Mahedi","Mridul", UserTypeEnum.ROLE_CUSTOMER);
        account = new Account("Mahedi",new BigDecimal(100), Account.AccountTypeEnum.CURRENT);
        account.setIban("NL55435435435435");
        account.setUser(userToCreate);

        transactions = new Transactions();
        transactions.setAccountfrom(account);
        transactions.setAccountto(transactions.getAccountto());
        transactions.setAmount(new BigDecimal(20.00));
        transactions.setTransactionDate(LocalDateTime.now());
        transactions.setUserperforming(Account.AccountTypeEnum.CURRENT);
    }


}
