package io.swagger.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    private Transactions transactions;
    private Account account;
    private UserToCreate userToCreate;



    @BeforeEach
    public void setup(){
        userToCreate = new UserToCreate("Erjano","123456","erjano@gmail.com","Erjano","Baku",UserTypeEnum.ROLE_CUSTOMER);
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

    @Test
    public void createTransactionShouldNotBeNull() {
        assertNotNull(transactions);
    }

    @Test
    public void setNegativeAmountShouldThrowError() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            transactions.setAmount(BigDecimal.valueOf(-500));
        });
    }
}
