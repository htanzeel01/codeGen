package io.swagger.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    private Transaction transaction;
    private Account account;
    private User user;



    @BeforeEach
    public void setup(){
        user = new User("Erjano","123456","erjano@gmail.com","Erjano","Baku",UserTypeEnum.ROLE_CUSTOMER);
        account = new Account(new BigDecimal(100), Account.AccountTypeEnum.CURRENT);
        account.setIban("NL55435435435435");
        account.setUser(user);

        transaction = new Transaction();
        transaction.setAccountfrom(account);
        transaction.setAccountto(transaction.getAccountto());
        transaction.setAmount(new BigDecimal(20.00));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setUserperforming(UserTypeEnum.ROLE_EMPLOYEE);
    }

    @Test
    public void createTransactionShouldNotBeNull() {
        assertNotNull(transaction);
    }

    @Test
    public void setNegativeAmountShouldThrowError() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            transaction.setAmount(BigDecimal.valueOf(-500));
        });
    }
}
